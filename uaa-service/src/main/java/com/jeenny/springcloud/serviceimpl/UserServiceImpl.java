package com.jeenny.springcloud.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeenny.springcloud.model.entity.User;
import com.jeenny.springcloud.mapper.UserMapper;
import com.jeenny.springcloud.model.entity.UserAuth;
import com.jeenny.springcloud.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService,UserDetailsService {

    @Autowired
    UserAuthServiceImpl authService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<UserAuth> authQuery = new QueryWrapper<>();
        authQuery.eq("identifier",s);
        UserAuth userAuth = authService.getOne(authQuery);
        if(userAuth != null){
//            List<UserAuth> authList = authService.list(new QueryWrapper<UserAuth>()
//                    .eq("user_id",userAuth.getUserId()));
            User user = this.getById(userAuth.getUserId());
//            user.setAuths(authList);
            return user;
        }
        return null;
    }

}
