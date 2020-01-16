package com.github.tangyi.common.security.tenant;

/**
 * ThreadLocal保存租户code
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 15:57
 */
public class TenantContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setContext(String tenantCode) {
        CONTEXT.set(tenantCode);
    }

    public static String getTenantCode() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
