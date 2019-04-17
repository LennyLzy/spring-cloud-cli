package com.jeenny.springcloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role")
public class Role implements Serializable,GrantedAuthority {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String roleName;

    private String description;


    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
