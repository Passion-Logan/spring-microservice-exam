package com.github.tangyi.common.core.exceptions;

/**
 * token非法异常
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:01
 */
public class InvalidAccessTokenException extends CommonException {

    private static final long serialVersionUID = -7285211528095468156L;

    public InvalidAccessTokenException() {
    }

    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
