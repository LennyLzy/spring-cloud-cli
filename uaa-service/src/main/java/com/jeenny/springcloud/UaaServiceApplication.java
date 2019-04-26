package com.jeenny.springcloud;

import com.jeenny.springcloud.annotation.EnableFeignOAuth2Client;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.jeenny.springcloud.mapper")
@Controller
@EnableFeignClients
//@EnableFeignOAuth2Client
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
	public String testFeign(){
		return "testFeign";
	}
}
