package com.jeenny.springcloud.serviceimpl;

import com.jeenny.springcloud.response.Result;
import com.jeenny.springcloud.service.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

/**
 * Created by Administrator on 2019/4/18.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    private static String USER_SERVICE_KEY = "user-service:123456";
    @Autowired
    UserServiceClient userServiceClient;
        @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            String encodedKey = Base64Utils.encodeToString(USER_SERVICE_KEY.getBytes());
            UserDetails result = userServiceClient.loadUserByUsername(s);
        if(result != null)
            return result;
        else
            throw new UsernameNotFoundException("user not found");
    }
}
