package com.github.tangyi.common.core.utils;

import com.github.tangyi.common.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.security.Principal;

/**
 * 系统工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 15:42
 */
@Slf4j
public class SysUtil {

    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NOPadding";

    /**
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        }
        if (principal instanceof Principal) {
            return ((Principal)principal).getName();
        }
        return String.valueOf(principal);
    }

    /**
     * 获取系统编号
     *
     * @return
     */
    public static String getSysCode() {
        return SecurityConstant.SYS_CODE;
    }

    /**
     * 获取租户编号
     *
     * @return
     */
    public static String getTenantCode() {
        String tenantCode = TenantContextHolder.getTenantCode();
        if (StringUtils.isBlank(tenantCode)) {
            tenantCode = getCurrentUserTenantCode();
        }
        if (StringUtils.isBlank(tenantCode)) {
            tenantCode = SecurityConstant.DEFAULT_TENANT_CODE;
        }
        return tenantCode;
    }

    private static String getCurrentUserTenantCode() {
        String tenantCode = "";
        try {
            ResourceServerTokenServices resourceServerTokenServices = SpringContextHolder.getApplicationContext().getBean(ResourceServerTokenServices.class);
        }
    }
}
