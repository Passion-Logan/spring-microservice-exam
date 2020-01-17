package com.github.tangyi.common.core.exceptions;

/**
 * 验证码过期异常
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:09
 */
public class ValidateCodeExpiredException extends CommonException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeExpiredException() {}

    public ValidateCodeExpiredException(String message) {
        super(message);
    }
}
