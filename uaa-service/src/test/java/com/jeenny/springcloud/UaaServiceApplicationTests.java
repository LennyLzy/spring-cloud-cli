package com.jeenny.springcloud;

import com.jeenny.springcloud.config.MyBatisPlusGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UaaServiceApplicationTests {

	@Test
	public void contextLoads() {
		MyBatisPlusGenerator.excute();
	}

}
