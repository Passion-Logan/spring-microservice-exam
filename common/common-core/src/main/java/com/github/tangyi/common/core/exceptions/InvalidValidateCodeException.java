package com.github.tangyi.common.core.exceptions;

/**
 * 验证码错误异常
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:02
 */
public class InvalidValidateCodeException extends CommonException {

    private static final long serialVersionUID = -7285211528095468156L;

    public InvalidValidateCodeException() {}

    public InvalidValidateCodeException(String message) {
        super(message);
    }
}
