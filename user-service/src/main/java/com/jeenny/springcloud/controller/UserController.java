package com.jeenny.springcloud.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeenny.springcloud.model.dto.UserLoginDTO;
import com.jeenny.springcloud.model.entity.User;
import com.jeenny.springcloud.service.UserService;
import com.jeenny.springcloud.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.jeenny.springcloud.response.Result;
import com.jeenny.springcloud.response.ResultUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lenny
 * @since 2019-04-15
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody JSONObject reqData){
        String authType = reqData.getString("authType");
        String identifier = reqData.getString("identifier");
        String credential = reqData.getString("credential");
        String nickname = reqData.getString("nickname");
        boolean isRegistered = false;
        if(StringUtils.isEmpty(identifier) || StringUtils.isEmpty(credential))
            ResultUtil.error(0,"账号/密码不能为空");
        else{
            isRegistered = userService.register(identifier,credential,authType);
        }
        return ResultUtil.success(isRegistered);
    }
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody JSONObject reqData) throws Exception {
        String identifier = reqData.getString("identifier");
        String credential = reqData.getString("credential");
        if(StringUtils.isEmpty(identifier) || StringUtils.isEmpty(credential))
            return ResultUtil.error(-1,"登陆失败");
        else{
            UserLoginDTO userLoginDTO = userService.login(identifier,credential);
            return ResultUtil.success(userLoginDTO);
        }
    }

    @PostMapping("/info/{id}")
    public Result getUserById(@PathVariable("id")String id){
        return ResultUtil.success(userService.getById(Long.valueOf(id)));
    }

    @PostMapping("/load")
    @ResponseBody
    public Result loadUserByUsername(@RequestParam("username")String username){
        return ResultUtil.success(userService.loadUserByUsername(username));
    }
}

