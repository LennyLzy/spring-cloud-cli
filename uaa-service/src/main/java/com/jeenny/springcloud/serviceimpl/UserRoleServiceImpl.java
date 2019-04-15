package com.jeenny.springcloud.serviceimpl;

import com.jeenny.springcloud.model.entity.UserRole;
import com.jeenny.springcloud.mapper.UserRoleMapper;
import com.jeenny.springcloud.service.UserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
