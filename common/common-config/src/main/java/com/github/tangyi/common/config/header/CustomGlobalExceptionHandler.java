package com.github.tangyi.common.config.header;

import com.github.tangyi.common.core.constant.ApiMsg;
import com.github.tangyi.common.core.exceptions.CommonException;
import com.github.tangyi.common.core.model.ResponseBean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * <p>
 * 代码描述
 * <p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved.
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 11:53
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理参数校验异常
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        // 获取所有异常信息
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ResponseBean<List<String>> responseBean = new ResponseBean<>(errors, ApiMsg.KEY_SERVICE, ApiMsg.ERROR);

        return new ResponseEntity<>(responseBean, headers, status);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResponseBean<String>> handleCommonException(Exception e) {
        ResponseBean<String> responseBean = new ResponseBean<>(e.getMessage(), ApiMsg.KEY_SERVICE, ApiMsg.ERROR);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
