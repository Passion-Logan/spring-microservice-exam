package com.github.tangyi.common.feign.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 日志拦截器
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 17:31
 */
public class LogInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        logger.debug(String.format("sending %s request %s%n%s", request.method(), request.url(), request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        logger.debug(String.format("received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d,
            response.headers()));
        return response;
    }
}
