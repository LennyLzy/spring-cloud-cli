package com.jeenny.springcloud.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeenny.springcloud.mapper.UserMapper;
import com.jeenny.springcloud.model.dto.UserLoginDTO;
import com.jeenny.springcloud.model.entity.User;
import com.jeenny.springcloud.model.entity.UserAuth;
import com.jeenny.springcloud.response.exception.CustomException;
import com.jeenny.springcloud.service.UaaServiceClient;
import com.jeenny.springcloud.service.UserService;
import com.jeenny.springcloud.dto.JWT;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Autowired
    UserAuthServiceImpl authService;
    @Autowired
    UaaServiceClient uaaServiceClient;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<UserAuth> authQuery = new QueryWrapper<>();
        authQuery.eq("identifier",s);
        UserAuth userAuth = authService.getOne(authQuery);
        if(userAuth != null){
            User user = this.getById(userAuth.getUserId());
            user.setUsername(userAuth.getIdentifier());
            user.setPassword(userAuth.getCredential());
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public UserLoginDTO login(String identifier, String credential) throws Exception {
        QueryWrapper<UserAuth> authQuery = new QueryWrapper<>();
        authQuery.eq("identifier",identifier);
        UserAuth userAuth = authService.getOne(authQuery);
        if(null == userAuth){
            throw new UsernameNotFoundException("user not found");
        }
        if("account".equals(userAuth.getAuthType())){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(!passwordEncoder.matches(credential,userAuth.getCredential()))
                throw new UsernameNotFoundException("password error");
            JWT jwt = uaaServiceClient.getToken(
                    "Basic "+ Base64Utils.encodeToString("app:123456".getBytes()),
                    "password",userAuth.getIdentifier(),credential);
            if(jwt == null)
                throw new CustomException(-1,"Feign Error");
            User user = this.getById(userAuth.getUserId());
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setUser(user);
            userLoginDTO.setJwt(jwt);
            return userLoginDTO;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean register(String identifier, String credential, String authType) {
        List<UserAuth> authList = authService.list(
                new QueryWrapper<UserAuth>()
                        .eq("auth_type",authType)
                        .eq("identifier",identifier)
        );
        if(authList.size() > 0)
            return false;
        else{
            User user = new User();
            user.setNickname("管理员");
            user.setAvatar("");
            this.save(user);
            System.out.println(user.getId());
            UserAuth userAuth = new UserAuth();
            userAuth.setAuthType(authType);
            userAuth.setIdentifier(identifier);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userAuth.setCredential(passwordEncoder.encode(credential));
            userAuth.setUserId(user.getId());
            authService.save(userAuth);
            return true;
        }
    }
}
