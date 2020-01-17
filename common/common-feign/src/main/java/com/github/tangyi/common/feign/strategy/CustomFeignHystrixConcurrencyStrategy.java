package com.github.tangyi.common.feign.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义隔离策略 解决RequestContextHolder.getRequestAttributes()问题
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 17:35
 */
@Slf4j
@Component
@Primary
public class CustomFeignHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy hystrixConcurrencyStrategy;

    public CustomFeignHystrixConcurrencyStrategy() {
        try {
            this.hystrixConcurrencyStrategy = HystrixPlugins.getInstance().getConcurrencyStrategy();
            if (this.hystrixConcurrencyStrategy instanceof CustomFeignHystrixConcurrencyStrategy) {
                return;
            }
            HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
            HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
            HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
            HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
            this.logCurrentStateOfHystrixPlugins(eventNotifier, metricsPublisher, propertiesStrategy);
            HystrixPlugins.reset();
            HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
            HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        } catch (Exception e) {
            log.error("Failed to register Sleuth Hystrix Concurrency Strategy", e);
        }

    }

    private void logCurrentStateOfHystrixPlugins(HystrixEventNotifier eventNotifier,
        HystrixMetricsPublisher metricsPublisher, HystrixPropertiesStrategy propertiesStrategy) {
        if (log.isDebugEnabled()) {
            log.debug(
                "Current Hystrix plugins configuration is [" + "concurrencyStrategy [" + this.hystrixConcurrencyStrategy
                    + "]," + "eventNotifier [" + eventNotifier + "]," + "metricPublisher [" + metricsPublisher + "],"
                    + "propertiesStrategy [" + propertiesStrategy + "]," + "]");
            log.debug("Registering Sleuth Hystrix Concurrency Strategy.");
        }
    }
}
