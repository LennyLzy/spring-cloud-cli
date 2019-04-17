package com.jeenny.springcloud.mapper;

import com.jeenny.springcloud.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> selectRoleByUid(@Param("uid")Long uid);
}
