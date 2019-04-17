package com.jeenny.springcloud;

import com.jeenny.springcloud.serviceimpl.RoleServiceImpl;
import com.jeenny.springcloud.serviceimpl.UserServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import response.Result;
import response.ResultUtil;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@MapperScan("com.jeenny.springcloud.mapper")
@RestController
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Autowired
	RoleServiceImpl roleService;
	@Autowired
	UserServiceImpl userService;

	@GetMapping("/test")
	public Result test(){
		return ResultUtil.success(userService.getById(1L));
	}

	@GetMapping("/foo")
//	@PreAuthorize("hasAuthority('超级管理员')")
	@PreAuthorize("has")
	public Result foo(){
		return ResultUtil.success();
	}
}