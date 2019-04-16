package com.jeenny.springcloud.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
@TableName("user")
public class User implements Serializable,UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nickname;

    private String avatar;

    @TableField(exist = false)
    private List<UserAuth> auths;

    @TableField(exist = false)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        for(UserAuth auth:auths){
            if("username".equals(auth.getAuthType())){
                return auth.getCredential();
            }
        }
        return null;
    }

    @Override
    public String getUsername() {
        for(UserAuth auth:auths){
            if("username".equals(auth.getAuthType())){
                return auth.getIdentifier();
            }
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
