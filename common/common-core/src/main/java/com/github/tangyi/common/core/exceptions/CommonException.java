package com.github.tangyi.common.core.exceptions;

/**
 * 公共异常<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 12:35
 */
public class CommonException extends RuntimeException{

    public static final long serialVersionUID = 1L;

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause, String message) {
        super(cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
