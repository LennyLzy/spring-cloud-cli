package com.jeenny.springcloud;

import com.jeenny.springcloud.annotation.EnableFeignOAuth2Client;
import com.jeenny.springcloud.model.entity.User;
import com.jeenny.springcloud.service.UaaServiceClient;
import com.jeenny.springcloud.serviceimpl.RoleServiceImpl;
import com.jeenny.springcloud.serviceimpl.UserServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.jeenny.springcloud.response.Result;
import com.jeenny.springcloud.response.ResultUtil;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@MapperScan("com.jeenny.springcloud.mapper")
@RestController
//@EnableFeignOAuth2Client
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Autowired
	RoleServiceImpl roleService;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	RedisTemplate redisTemplate;

	@GetMapping("/test")
	public Result test(){
		User user = userService.getById(1);
		redisTemplate.opsForValue().set("user",user);
		return ResultUtil.success(redisTemplate.opsForValue().get("user"));
//		return ResultUtil.success(userService.getById(1L));
	}

	@GetMapping("/foo")
//	@PreAuthorize("hasAuthority('超级管理员')")
	public Result foo(){
		return ResultUtil.success();
	}

	@Autowired
	UaaServiceClient uaaServiceClient;
	@GetMapping("/testfeign")
	@ResponseBody
	public Result testFeign(){
		return ResultUtil.success(uaaServiceClient.testFeign());
	}
}
