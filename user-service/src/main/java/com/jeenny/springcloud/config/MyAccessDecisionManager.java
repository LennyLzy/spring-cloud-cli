package com.jeenny.springcloud.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import java.util.*;

/**
 * Created by Administrator on 2019/4/17.
 */
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        Set<String> authSet = new HashSet<>();
        for(GrantedAuthority ga : authentication.getAuthorities()){
            authSet.add(ga.getAuthority());
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        SecurityConfig sc;
        while (ite.hasNext()) {
            sc = (SecurityConfig)ite.next();
            if("permitAll".equals(sc.getAttribute()) || "ROLE_ANONYMOUS".equals(sc.getAttribute()))
                return;
            if(authSet.contains(sc.getAttribute()))
                return;
        }
        throw new AccessDeniedException("not allow");
    }
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
