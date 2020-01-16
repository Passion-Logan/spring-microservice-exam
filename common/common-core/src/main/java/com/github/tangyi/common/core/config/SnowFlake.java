package com.github.tangyi.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.tangyi.common.core.properties.SnowflakeProperties;
import com.github.tangyi.common.core.utils.SnowflakeIdWorker;

import lombok.AllArgsConstructor;

/**
 * ID生成配置
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 18:28
 */
@Configuration
@AllArgsConstructor
public class SnowFlake {

    private final SnowflakeProperties snowflakeProperties;

    @Bean
    public SnowflakeIdWorker initTokenWorker() {
        return new SnowflakeIdWorker(Integer.parseInt(snowflakeProperties.getWorkId()),
            Integer.parseInt(snowflakeProperties.getDataCenterId()));
    }
}
