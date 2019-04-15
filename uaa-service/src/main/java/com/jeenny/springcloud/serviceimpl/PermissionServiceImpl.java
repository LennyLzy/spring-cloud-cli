package com.jeenny.springcloud.serviceimpl;

import com.jeenny.springcloud.model.entity.Permission;
import com.jeenny.springcloud.mapper.PermissionMapper;
import com.jeenny.springcloud.service.PermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
