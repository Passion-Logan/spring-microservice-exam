package com.github.tangyi.common.core.utils;

import java.util.UUID;

/**
 * id生成工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:15
 */
public class IdGen {

    /**
     * 封装JDK自带的UUID, 中间无-分割.
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 基于snowflake算法生成ID
     *
     * @return
     */
    public static long snowflakeId() {
        return SpringContextHolder.getApplicationContext().getBean(SnowflakeIdWorker.class).nextId();
    }
}
