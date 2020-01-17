package com.github.tangyi.common.log.event;

import com.github.tangyi.common.core.model.Log;
import org.springframework.context.ApplicationEvent;

/**
 * 日志事件
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 18:14
 */
public class LogEvent extends ApplicationEvent {

    public LogEvent(Log source) {
        super(source);
    }
}
