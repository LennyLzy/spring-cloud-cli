package com.jeenny.springcloud.annotation;

import com.jeenny.springcloud.config.OAuth2FeignConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/4/22.
 */
@Import({OAuth2FeignConfig.class})
@EnableOAuth2Client
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnableFeignOAuth2Client {

}