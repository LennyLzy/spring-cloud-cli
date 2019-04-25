package com.jeenny.springcloud.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeenny.springcloud.dto.JWT;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;

/**
 * Created by Administrator on 2019/4/19.
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(FeignInterceptor.class);

    private String CLIENT_ID = "feign";
    private String CLIENT_SECRET = "123456";
    private String TOKEN_URL = "http://localhost:18050/oauth/token";
    private OkHttpClient client = new OkHttpClient();
    private String credential = Credentials.basic(CLIENT_ID, CLIENT_SECRET);
    private FormBody body = new FormBody.Builder()
            .add("grant_type", "client_credentials")
            .build();
    private JWT jwt;

    public FeignInterceptor(){
    }

    public void apply(RequestTemplate requestTemplate){
        if(!requestTemplate.headers().containsKey("Authorization")){
            if(this.jwt == null)
                getToken();
            System.out.println(jwt);
            requestTemplate.header("Authorization",
                    String.format("%s %s",jwt.getToken_type(),jwt.getAccess_token()));
        }
    }

    private void getToken(){
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(TOKEN_URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            ObjectMapper mapper = new ObjectMapper();
            JWT jwt = mapper.readValue(response.body().string(), JWT.class);
            this.jwt = jwt;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearJWT(){
        this.jwt = null;
    }

}

