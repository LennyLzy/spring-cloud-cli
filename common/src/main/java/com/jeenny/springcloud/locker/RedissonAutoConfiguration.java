package com.jeenny.springcloud.locker;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Configuration
public class RedissonAutoConfiguration {

//    /**
//     * 哨兵模式自动装配
//     * @return
//     */
//    @Bean
//    @ConditionalOnProperty(name="redisson.master-name")
//    RedissonClient redissonSentinel() {
//        Config config = new Config();
//        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redssionProperties.getSentinelAddresses())
//                .setMasterName(redssionProperties.getMasterName())
//                .setTimeout(redssionProperties.getTimeout())
//                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
//                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
//
//        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
//            serverConfig.setPassword(redssionProperties.getPassword());
//        }
//        return Redisson.create(config);
//    }

//    /**
//     * 单机模式自动装配
//     * @return
//     */
//    @Bean
//    @ConditionalOnProperty(name="redisson.address")
//    RedissonClient redissonSingle() {
//        Config config = new Config();
//        SingleServerConfig serverConfig = config.useSingleServer()
//                .setAddress(redssionProperties.getAddress())
//                .setTimeout(redssionProperties.getTimeout())
//                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
//                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
//
//        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
//            serverConfig.setPassword(redssionProperties.getPassword());
//        }
//
//        return Redisson.create(config);
//    }

    @Bean
    RedissonClient redissonSingle() throws IOException {
//        Config config = Config.fromYAML(new File("classpath:redisson-single-conf.yml"));
        Config config = Config.fromYAML(new ClassPathResource("redisson-single-conf.yml").getInputStream());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new RedissonDistributedLocker(redissonClient);
        RedisLockUtil.setLocker(locker);
        return locker;
    }

}