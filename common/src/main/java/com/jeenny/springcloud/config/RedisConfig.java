package com.jeenny.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/9.
 */
@Configuration
@EnableCaching
//@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
//            sb.append(target.getClass().getName());
            sb.append(target.getClass().getSimpleName()+"?");
//            sb.append("$" + method.getName());
            for (Object obj : params) {
                sb.append(obj.toString()+"&");
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisTemplate<String,Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Serializable> template = new RedisTemplate<String,Serializable>();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        System.out.println(redisConnectionFactory.getClass().getName());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    public StringRedisSerializer stringRedisSerializer(){
        return new StringRedisSerializer();
    }
    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }
    @Autowired
    StringRedisSerializer stringRedisSerializer;
    @Autowired
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(30), // 默认策略，未配置的 namespace 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 namespace 策略
                // namespane 由 @Cacheable等注解的value属性指定，但value不能为“”
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("user", this.getRedisCacheConfigurationWithTtl(3600));
//        redisCacheConfigurationMap.put("UserInfoListAnother", this.getRedisCacheConfigurationWithTtl(18000));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(genericJackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }

//    @Autowired
//    RedisMessageListener redisMessageListener;

//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        //container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@0__:expired"));
//        //ChannelTopic指的是频道的名称，__keyevent@0__:expired 是发布key过期信息的频道
//        container.addMessageListener(listenerAdapter,new ChannelTopic("__keyevent@0__:expired"));
//        container.addMessageListener(listenerAdapter,new ChannelTopic("demo"));
//        return container;
//    }
//    @Bean
//    MessageListenerAdapter listenerAdapter() {
//        return new MessageListenerAdapter(redisMessageListener);
//    }
}