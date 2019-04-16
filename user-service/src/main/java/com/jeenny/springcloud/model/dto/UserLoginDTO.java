package com.jeenny.springcloud.model.dto;

import com.jeenny.springcloud.model.entity.User;
import dto.JWT;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;


/**
 * Created by Administrator on 2019/4/16.
 */
@Data
public class UserLoginDTO {
    private User user;
    private JWT jwt;
}
