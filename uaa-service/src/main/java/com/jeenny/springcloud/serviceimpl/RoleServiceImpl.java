package com.jeenny.springcloud.serviceimpl;

import com.jeenny.springcloud.model.entity.Role;
import com.jeenny.springcloud.mapper.RoleMapper;
import com.jeenny.springcloud.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
