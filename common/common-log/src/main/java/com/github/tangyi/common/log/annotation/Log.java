package com.github.tangyi.common.log.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 17:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 描述
     *
     * @return {String}
     */
    String value();

}
