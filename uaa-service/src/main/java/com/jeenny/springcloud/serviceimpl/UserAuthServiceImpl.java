package com.jeenny.springcloud.serviceimpl;

import com.jeenny.springcloud.model.entity.UserAuth;
import com.jeenny.springcloud.mapper.UserAuthMapper;
import com.jeenny.springcloud.service.UserAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

}
