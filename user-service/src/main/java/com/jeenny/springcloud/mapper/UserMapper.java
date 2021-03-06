package com.jeenny.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeenny.springcloud.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
public interface UserMapper extends BaseMapper<User> {
    User selectById(@Param("var1")Serializable var1);
}
