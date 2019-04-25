package com.jeenny.springcloud.service;

import com.jeenny.springcloud.servicefallback.UaaServiceHystrix;
import com.jeenny.springcloud.dto.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2019/4/16.
 */
@FeignClient(value = "uaa-service", fallback = UaaServiceHystrix.class)
public interface UaaServiceClient {
    @PostMapping("/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization")String authorization, @RequestParam("grant_type")String type,
                 @RequestParam("username")String username,@RequestParam("password")String password);
    @GetMapping("/testfeign")
    String testFeign();
}
