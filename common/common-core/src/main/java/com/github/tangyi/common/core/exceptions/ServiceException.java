package com.github.tangyi.common.core.exceptions;

/**
 * 服务异常
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:04
 */
public class ServiceException extends CommonException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }
}
