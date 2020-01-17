package com.github.tangyi.common.log.utils;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.tangyi.common.core.constant.CommonConstant;
import com.github.tangyi.common.core.model.Log;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;

/**
 * 日志工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 17:55
 */
public class LogUtil {

    public static Log getLog() {
        HttpServletRequest request =
            ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        Log sysLog = new Log();
        sysLog.setCreator(Objects.requireNonNull(getUsername()));
        sysLog.setType(CommonConstant.STATUS_NORMAL);
        sysLog.setIp(ServletUtil.getClientIP(request));
        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        sysLog.setMethod(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
        sysLog.setServiceId(getClientId());
        return sysLog;
    }

    /**
     * 获取客户端
     *
     * @return
     */
    private static String getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication)authentication;
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        return null;
    }

    /**
     * 获取用户名称
     *
     * @return
     */
    private static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }
}
