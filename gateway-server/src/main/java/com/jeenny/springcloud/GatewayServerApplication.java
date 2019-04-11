package com.jeenny.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableEurekaClient
@RestController
//@EnableHystrix
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

//	@Bean
//	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//		c.setIgnoreUnresolvablePlaceholders(true);
//		return c;
//	}

	@Value("${foo}")
	private String foo;

	@GetMapping("/foo")
	public String foo(){
		return foo;
	}

	@RequestMapping(value = "/fallback")
	public String fallback(){
		return "error";
	}

	@RequestMapping("/timeout")
	//放在别的服务请求，测试熔断，放在同一个网关服务里熔断不生效
	public String timeout() throws InterruptedException{

		//睡5秒，网关Hystrix3秒超时，会触发熔断降级操作
		Thread.sleep(8000);

		return "timeout";
	}
}
