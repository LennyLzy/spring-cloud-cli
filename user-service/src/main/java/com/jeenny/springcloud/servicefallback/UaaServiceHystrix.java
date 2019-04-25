package com.jeenny.springcloud.servicefallback;

import com.jeenny.springcloud.service.UaaServiceClient;
import com.jeenny.springcloud.dto.JWT;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/4/16.
 */
@Component
public class UaaServiceHystrix implements UaaServiceClient {
    @Override
    public JWT getToken(String aauthorization, String type, String username, String password) {
        return null;
    }

    @Override
    public String testFeign() {
        return null;
    }
}
