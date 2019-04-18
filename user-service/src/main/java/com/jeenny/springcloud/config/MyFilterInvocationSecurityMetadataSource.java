package com.jeenny.springcloud.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * Created by Administrator on 2019/4/17.
 */
public class MyFilterInvocationSecurityMetadataSource implements org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final Map<String,String> urlRoleMap = new LinkedHashMap <>();

    static {
        urlRoleMap.put("/test","ROLE_ANONYMOUS");
        urlRoleMap.put("/user/**","authenticated");
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
//        String httpMethod = fi.getRequest().getMethod();
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
        for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){
                return SecurityConfig.createList(entry.getValue());
            }
        }
        return SecurityConfig.createList("ROLE_USER");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
