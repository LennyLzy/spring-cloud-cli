package com.jeenny.springcloud.serviceimpl;

import com.jeenny.springcloud.model.entity.RolePermission;
import com.jeenny.springcloud.mapper.RolePermissionMapper;
import com.jeenny.springcloud.service.RolePermissionService;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
