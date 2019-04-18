package com.jeenny.springcloud.servicefallback;

import com.jeenny.springcloud.response.Result;
import com.jeenny.springcloud.service.UserServiceClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/4/18.
 */
@Component
public class UserServiceHystrix implements UserServiceClient {

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }
}
