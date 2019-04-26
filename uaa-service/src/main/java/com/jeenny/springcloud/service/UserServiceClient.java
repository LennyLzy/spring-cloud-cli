package com.jeenny.springcloud.service;

import com.jeenny.springcloud.model.entity.User;
import com.jeenny.springcloud.response.Result;
import com.jeenny.springcloud.response.ResultUtil;
import com.jeenny.springcloud.servicefallback.UserServiceHystrix;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2019/4/18.
 */
@FeignClient(value = "user-service",fallback = UserServiceHystrix.class)
public interface UserServiceClient {
    @PostMapping("/user/load")
    @ResponseBody
    Result<User> loadUserByUsername(@RequestParam("username")String username);
}
