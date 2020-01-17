package com.github.tangyi.common.core.exceptions;

/**
 * 租户不存在异常
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:09
 */
public class TenantNotFoundException extends CommonException {

    private static final long serialVersionUID = -7285211528095468156L;

    public TenantNotFoundException() {}

    public TenantNotFoundException(String message) {
        super(message);
    }
}
