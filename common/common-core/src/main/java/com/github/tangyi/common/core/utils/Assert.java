package com.github.tangyi.common.core.utils;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:03
 */
public class Assert {

    /**
     * 非空校验
     *
     * @param object  object
     * @param message message
     * @author tangyi
     * @date 2019/01/23 20:00
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
