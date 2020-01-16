package com.github.tangyi.common.core.config;

import com.github.tangyi.common.core.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.tangyi.common.core.properties.SysProperties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统启动时的一些处理
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 18:04
 */
@Slf4j
@AllArgsConstructor
@Component
public class AppStartupRunner implements CommandLineRunner {

    private final SysProperties sysProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("================ start command line ================ ");
        // 设置系统属性
        if (StringUtils.isNotBlank(sysProperties.getCacheExpire())) {
            System.setProperty(CommonConstant.CACHE_EXPIRE, sysProperties.getCacheExpire());
        }
        log.info("================ end command line ================");
    }

}
