package com.jeenny.springcloud.config;


import com.jeenny.springcloud.dto.JWT;
import com.jeenny.springcloud.interceptor.FeignInterceptor;
import feign.*;
import feign.Logger;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.slf4j.*;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;

import static feign.FeignException.errorStatus;

/**
 * Created by Administrator on 2019/4/22.
 */
@Component
public class OAuth2FeignConfig {


    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Autowired
    FeignInterceptor feignInterceptor;

//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor(){
//        return new FeignInterceptor(FEIGN_TOKEN);
//    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer retry() {
        // default Retryer will retry 5 times waiting waiting
        // 100 ms per retry with a 1.5* back off multiplier
        return new Retryer.Default();
    }

    @Bean
    public Decoder feignDecoder() {
        return new CustomResponseEntityDecoder(new SpringDecoder(this.messageConverters),feignInterceptor);
    }

    class CustomResponseEntityDecoder implements Decoder {

        private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

        private Decoder decoder;
        private FeignInterceptor feignInterceptor;

        public CustomResponseEntityDecoder(Decoder decoder,FeignInterceptor feignInterceptor){
            this.decoder = decoder;
            this.feignInterceptor = feignInterceptor;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
//            feignInterceptor.clearJWT();
//            if(response.status() == HttpStatus.UNAUTHORIZED.value()){
//                feignInterceptor.clearJWT();
//                throw new RetryableException("access_token过期，即将进行重试", Request.HttpMethod.POST, new Date());
//            }
            return decoder.decode(response, type);
        }
    }

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new RestClientErrorDecoder(FEIGN_TOKEN);
//    }
//    /**
//     *  Http响应成功 但是token失效，需要定制 ResponseEntityDecoder
//     * @author maxianming
//     * @date 2018/10/30 9:47
//     */
//    class CustomResponseEntityDecoder implements Decoder {
//        private org.slf4j.Logger log = LoggerFactory.getLogger(CustomResponseEntityDecoder.class);
//
//        private Decoder decoder;
//        private JWT jwt;
//
//        public CustomResponseEntityDecoder(Decoder decoder,JWT jwt) {
//            this.decoder = decoder;
//            this.jwt = jwt;
//        }
//
//        @Override
//        public Object decode(final Response response, Type type) throws IOException, FeignException {
//            if (log.isDebugEnabled()) {
//                log.debug("feign decode type:{}，reponse:{}", type, response.body());
//            }
//            if (isParameterizeHttpEntity(type)) {
//                type = ((ParameterizedType) type).getActualTypeArguments()[0];
//                Object decodedObject = decoder.decode(response, type);
//                return createResponse(decodedObject, response);
//            }
//            else if (isHttpEntity(type)) {
//                return createResponse(null, response);
//            }
//            else {
//                // custom ResponseEntityDecoder if token is valid then go to errorDecoder
//                String body = Util.toString(response.body().asReader());
//                if (body.contains(HttpStatus.UNAUTHORIZED.name())) {
//                    clearTokenAndRetry(response, body);
//                }
//                return decoder.decode(response, type);
//            }
//        }
//
//        /**
//         * token失效 则将token设置为null 然后重试
//         * @author maxianming
//         * @param
//         * @return
//         * @date 2018/10/30 10:05
//         */
//        private void clearTokenAndRetry(Response response, String body) throws FeignException {
//            log.error("接收到Feign请求资源响应,响应内容:{}",body);
//            jwt = null;
//            throw new RetryableException("access_token过期，即将进行重试", Request.HttpMethod.POST, new Date());
//        }
//
//        private boolean isParameterizeHttpEntity(Type type) {
//            if (type instanceof ParameterizedType) {
//                return isHttpEntity(((ParameterizedType) type).getRawType());
//            }
//            return false;
//        }
//
//        private boolean isHttpEntity(Type type) {
//            if (type instanceof Class) {
//                Class c = (Class) type;
//                return HttpEntity.class.isAssignableFrom(c);
//            }
//            return false;
//        }
//
//        @SuppressWarnings("unchecked")
//        private <T> ResponseEntity<T> createResponse(Object instance, Response response) {
//
//            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//            for (String key : response.headers().keySet()) {
//                headers.put(key, new LinkedList<>(response.headers().get(key)));
//            }
//            return new ResponseEntity<>((T) instance, headers, org.springframework.http.HttpStatus.valueOf(response
//                    .status()));
//        }
//    }
//
//    /**
//     *  Feign调用HTTP返回响应码错误时候，定制错误的解码
//     * @author maxianming
//     * @date 2018/10/30 9:45
//     */
//    class RestClientErrorDecoder implements ErrorDecoder {
//        private org.slf4j.Logger logger = LoggerFactory.getLogger(RestClientErrorDecoder.class);
//
////        private OAuth2ClientContext context;
//        private JWT jwt;
//        RestClientErrorDecoder(JWT jwt) {
//            this.jwt = jwt;
//        }
//
//        public Exception decode(String methodKey, Response response) {
////            logger.error("Feign调用异常，异常methodKey:{}, token:{}, response:{}", methodKey, context.getAccessToken(), response.body());
//            if (HttpStatus.UNAUTHORIZED.value() == response.status()) {
//                logger.error("接收到Feign请求资源响应401，access_token已经过期，重置access_token为null待重新获取。");
////                context.setAccessToken(null);
//                jwt = null;
//                return new RetryableException("疑似access_token过期，即将进行重试",Request.HttpMethod.POST, new Date());
//            }
//            return errorStatus(methodKey, response);
//        }
//    }
}
