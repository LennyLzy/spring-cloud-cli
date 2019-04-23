package com.jeenny.springcloud.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeenny.springcloud.dto.JWT;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;

/**
 * Created by Administrator on 2019/4/19.
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(FeignInterceptor.class);

    public void apply(RequestTemplate requestTemplate){
//        requestTemplate.header("Authorization", "Basic dWFhLXNlcnZpY2U6MTIzNDU2");
        //dXNlci1zZXJ2aWNlOjEyMzQ1Ng==
        if(!requestTemplate.headers().containsKey("Authorization")){
            OkHttpClient client = new OkHttpClient();
            String credential = Credentials.basic("feign", "123456");
            FormBody body = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .build();
            Request request = new Request.Builder()
                    .header("Authorization", credential)
                    .url("http://localhost:18050/oauth/token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                ObjectMapper mapper = new ObjectMapper();
                JWT jwt = mapper.readValue(response.body().string(), JWT.class);
                requestTemplate.header("Authorization",
                        String.format("%s %s",jwt.getToken_type(),jwt.getAccess_token()));
                System.out.println(jwt);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

