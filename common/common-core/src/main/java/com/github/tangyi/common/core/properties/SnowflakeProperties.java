package com.github.tangyi.common.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * ID生成配置
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 18:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cluster")
public class SnowflakeProperties {

    /**
     * 工作节点ID
     */
    private String workId;

    /**
     * 数据中心ID
     */
    private String dataCenterId;

}
