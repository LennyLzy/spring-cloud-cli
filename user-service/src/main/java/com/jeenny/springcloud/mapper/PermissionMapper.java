package com.jeenny.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeenny.springcloud.model.entity.Permission;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectURLPermissionRole();
}
