package com.github.tangyi.common.feign.config;

import java.util.concurrent.TimeUnit;

import com.github.tangyi.common.feign.interceptor.LogInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * okHttp配置
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 17:26
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
            // 连接超时
            .connectTimeout(60, TimeUnit.SECONDS)
            // 读超时
            .readTimeout(60, TimeUnit.SECONDS)
            // 写超时
            .writeTimeout(60, TimeUnit.SECONDS)
            // 是否自动重连
            .retryOnConnectionFailure(true).connectionPool(new ConnectionPool())
            // 日志拦截器
            // .addInterceptor(new LogInterceptor())
            .build();
    }
}
