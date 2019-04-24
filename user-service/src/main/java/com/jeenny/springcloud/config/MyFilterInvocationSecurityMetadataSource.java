package com.jeenny.springcloud.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * Created by Administrator on 2019/4/17.
 */
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource superMetadataSource;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final Map<String,String> urlRoleMap = new LinkedHashMap <>();

    private static String PERMITALL = "IS_AUTHENTICATED_ANONYMOUSLY";
    private static String AUTHENTICATED = "IS_AUTHENTICATED_FULLY";
    private static String REMENBERED = "IS_AUTHENTICATED_REMEMBERED";

    static {
        urlRoleMap.put("/test","permitAll");
        urlRoleMap.put("/user/login","ROLE_ANONYMOUS");
        urlRoleMap.put("/user/register","ROLE_ANONYMOUS");
        urlRoleMap.put("/user/**","ROLE_CLIENT");
    }

    public MyFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource){
        this.superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
        // TODO 从数据库加载权限配置
    }

    public MyFilterInvocationSecurityMetadataSource(){
        this.superMetadataSource = null;
        // TODO 从数据库加载权限配置
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
//        String httpMethod = fi.getRequest().getMethod();
//        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
        for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){
                return SecurityConfig.createList(entry.getValue().split(","));
            }
        }
        return SecurityConfig.createList("ROLE_USER","ROLE_CLIENT");
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
