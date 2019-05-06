package com.jeenny.springcloud;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.jeenny.springcloud.mapper")
@Controller
@EnableFeignClients
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

	@GetMapping("/test")
	public String test(){
		return "test";
	}

	@GetMapping("/testfeign")
	@ResponseBody
	public String testFeign(HttpServletRequest req){
		System.out.println(req.getHeader("Authorization"));
		return "testFeign";
	}
}
