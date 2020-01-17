package com.github.tangyi.common.core.cache;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import com.github.tangyi.common.core.utils.Assert;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;

/**
 * 源码：org.springframework.data.redis.cache.DefaultRedisCacheWriter
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 14:27
 */
public class CustomRedisCacheWriter implements RedisCacheWriter {

    private final RedisConnectionFactory connectionFactory;
    private final Duration sleepTime;

    /**
     * @param connectionFactory
     *            must not be {@literal null}.
     */
    public CustomRedisCacheWriter(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, Duration.ZERO);
    }

    /**
     * @param connectionFactory
     *            must not be {@literal null}.
     * @param sleepTime
     *            sleep time between lock request attempts. Must not be {@literal null}. Use {@link Duration#ZERO} to
     *            disable locking.
     */
    public CustomRedisCacheWriter(RedisConnectionFactory connectionFactory, Duration sleepTime) {

        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");

        this.connectionFactory = connectionFactory;
        this.sleepTime = sleepTime;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.redis.cache.RedisCacheWriter#put(java.lang.String, byte[], byte[],
     *      java.time.Duration)
     * @param name
     * @param key
     * @param value
     * @param ttl
     */
    @Override
    public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");

        execute(name, connection -> {
            if (shouldExpireWithin(ttl)) {
                connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS),
                    RedisStringCommands.SetOption.upsert());
            } else {
                connection.set(key, value);
            }

            return "OK";
        });
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.redis.cache.RedisCacheWriter#get(java.lang.String, byte[])
     */
    @Override
    public byte[] get(String name, byte[] key) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");

        return execute(name, connection -> connection.get(key));
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.redis.cache.RedisCacheWriter#putIfAbsent(java.lang.String, byte[], byte[],
     *      java.time.Duration)
     */
    @Override
    public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");

        return execute(name, connection -> {
            if (isLockingCacheWriter()) {
                doLock(name, connection);
            }

            try {
                if (connection.setNX(key, value)) {

                    if (shouldExpireWithin(ttl)) {
                        connection.pExpire(key, ttl.toMillis());
                    }
                    return null;
                }
                return connection.get(key);
            } finally {

                if (isLockingCacheWriter()) {
                    doUnLock(name, connection);
                }
            }
        });
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.redis.cache.RedisCacheWriter#remove(java.lang.String, byte[])
     */
    @Override
    public void remove(String name, byte[] key) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");

        execute(name, connection -> connection.del(key));
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.redis.cache.RedisCacheWriter#clean(java.lang.String, byte[])
     */
    @Override
    public void clean(String name, byte[] pattern) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(pattern, "Pattern must not be null!");

        execute(name, connection -> {

            boolean wasLocked = false;

            try {

                if (isLockingCacheWriter()) {
                    doLock(name, connection);
                    wasLocked = true;
                }

                byte[][] keys =
                    Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet()).toArray(new byte[0][]);

                if (keys.length > 0) {
                    connection.del(keys);
                }
            } finally {

                if (wasLocked && isLockingCacheWriter()) {
                    doUnLock(name, connection);
                }
            }

            return "OK";
        });

    }

    /**
     * Explicitly remove a write lock from a cache.
     *
     * @param name
     *            the name of the cache to unlock.
     */
    void lock(String name) {
        execute(name, connection -> doLock(name, connection));
    }

    void unlock(String name) {
        executeLockFree(connection -> doUnLock(name, connection));
    }

    private Boolean doLock(String name, RedisConnection connection) {
        return connection.setNX(createCacheLockKey(name), new byte[0]);
    }

    private Long doUnLock(String name, RedisConnection connection) {
        return connection.del(createCacheLockKey(name));
    }

    boolean doCheckLock(String name, RedisConnection connection) {
        return connection.exists(createCacheLockKey(name));
    }

    /**
     * @return {@literal true} if {@link RedisCacheWriter} uses locks.
     */
    private boolean isLockingCacheWriter() {
        return !sleepTime.isZero() && !sleepTime.isNegative();
    }

    private <T> T execute(String name, Function<RedisConnection, T> callback) {
        RedisConnection connection = connectionFactory.getConnection();
        try {
            checkAndPotentiallyWaitUntilUnlocked(name, connection);
            return callback.apply(connection);
        } finally {
            connection.close();
        }
    }

    private void executeLockFree(Consumer<RedisConnection> callback) {

        RedisConnection connection = connectionFactory.getConnection();

        try {
            callback.accept(connection);
        } finally {
            connection.close();
        }
    }

    private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {

        if (!isLockingCacheWriter()) {
            return;
        }

        try {

            while (doCheckLock(name, connection)) {
                Thread.sleep(sleepTime.toMillis());
            }
        } catch (InterruptedException ex) {

            // Re-interrupt current thread, to allow other participants to react.
            Thread.currentThread().interrupt();

            throw new PessimisticLockingFailureException(
                String.format("Interrupted while waiting to unlock cache %s", name), ex);
        }
    }

    private static boolean shouldExpireWithin(@Nullable Duration ttl) {
        return ttl != null && !ttl.isZero() && !ttl.isNegative();
    }

    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }

}
