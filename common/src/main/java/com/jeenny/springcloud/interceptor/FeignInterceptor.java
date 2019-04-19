package com.jeenny.springcloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/4/19.
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    public void apply(RequestTemplate requestTemplate){
//        requestTemplate.header("Authorization", "Basic dWFhLXNlcnZpY2U6MTIzNDU2");
        if(!requestTemplate.headers().containsKey("Authorization")){
            requestTemplate.header("Authorization", "Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==");
        }
    }

}

