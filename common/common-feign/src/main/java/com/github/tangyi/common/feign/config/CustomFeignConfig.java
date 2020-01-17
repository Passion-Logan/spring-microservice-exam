package com.github.tangyi.common.feign.config;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 服务间调用携带Authorization、Tenant-Code请求头
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 17:19
 */
@Configuration
public class CustomFeignConfig implements RequestInterceptor {

    private static final String TOKEN_HEADER = "authorization";

    private static final String TENANT_HEADER = "Tenant-Code";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (request != null) {
            requestTemplate.header(TOKEN_HEADER, getHeaders(request).get(TOKEN_HEADER));
            requestTemplate.header(TENANT_HEADER, getHeaders(request).get(TENANT_HEADER));
        }

    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            // hystrix隔离策略会导致RequestContextHolder.getRequestAttributes()返回null
            // 解决方案：http://www.itmuch.com/spring-cloud-sum/hystrix-threadlocal/
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
