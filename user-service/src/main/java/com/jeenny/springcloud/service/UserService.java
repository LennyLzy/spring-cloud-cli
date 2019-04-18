package com.jeenny.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeenny.springcloud.model.dto.UserLoginDTO;
import com.jeenny.springcloud.model.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
public interface UserService extends IService<User> {

    UserLoginDTO login(String identifier, String credential) throws Exception;

    boolean register(String identifier, String credential, String authType);
}
