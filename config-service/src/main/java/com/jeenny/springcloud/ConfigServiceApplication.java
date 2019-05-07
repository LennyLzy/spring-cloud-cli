package com.jeenny.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
@RestController
public class ConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServiceApplication.class, args);
	}

	@RequestMapping("/timeout")
	//复制到别的下游服务里测试熔断，放在网关服务里熔断不生效，熔断生效会转发到'/fallback'
	public String timeout() throws InterruptedException{

		//睡5秒，网关Hystrix3秒超时，会触发熔断降级操作
		Thread.sleep(4000);

		return "timeout";
	}
}
