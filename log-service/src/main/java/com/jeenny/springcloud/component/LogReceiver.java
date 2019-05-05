package com.jeenny.springcloud.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2019/4/28.
 */
@Component
public class LogReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

}
