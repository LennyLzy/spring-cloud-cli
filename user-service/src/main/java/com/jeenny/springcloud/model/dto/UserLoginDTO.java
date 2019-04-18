package com.jeenny.springcloud.model.dto;

import com.jeenny.springcloud.model.entity.User;
import com.jeenny.springcloud.dto.JWT;
import lombok.Data;


/**
 * Created by Administrator on 2019/4/16.
 */
@Data
public class UserLoginDTO {
    private User user;
    private JWT jwt;
}
