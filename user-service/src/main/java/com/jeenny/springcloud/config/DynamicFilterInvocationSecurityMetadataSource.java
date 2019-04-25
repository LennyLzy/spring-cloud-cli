package com.jeenny.springcloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Lenny on 2019/4/17.
 */
public final class DynamicFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    public DynamicFilterInvocationSecurityMetadataSource(){
        init();
    }

    private void init(){

    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet();
        Iterator var2 = this.requestMap.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry = (Map.Entry)var2.next();
            allAttributes.addAll((Collection)entry.getValue());
        }

        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        HttpServletRequest request = ((FilterInvocation)object).getRequest();
        Iterator var3 = this.requestMap.entrySet().iterator();

        Map.Entry entry;
        do {
            if(!var3.hasNext()) {
                return null;
            }

            entry = (Map.Entry)var3.next();
        } while(!((RequestMatcher)entry.getKey()).matches(request));

        return (Collection)entry.getValue();
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
