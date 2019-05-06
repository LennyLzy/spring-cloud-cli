package com.jeenny.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by Administrator on 2019/4/16.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.inMemory()
                    .withClient("feign")
                    .secret(passwordEncoder.encode("123456"))
                    .scopes("client")
                    .authorizedGrantTypes("Implicit","client_credentials")
                    .accessTokenValiditySeconds(5)
                    .authorities("ROLE_CLIENT")
                .and()
                    .withClient("app")
                    .secret(passwordEncoder.encode("123456"))
                    .scopes("app")
                    .authorizedGrantTypes("authorization_code","refresh_token","password")
                    .accessTokenValiditySeconds(3600)
                    .refreshTokenValiditySeconds(3600 * 24 * 7)
                    .authorities("ROLE_APP")
                .and()
                    .withClient("user")
                    .secret(passwordEncoder.encode("123456"))
                    .scopes("user")
                    .authorizedGrantTypes("authorization_code","refresh_token","password")
                    .accessTokenValiditySeconds(3600)
                    .refreshTokenValiditySeconds(3600 * 24 * 7)
                    .authorities("ROLE_USER")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauth){
        oauth
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    protected JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123456");  //对称加密
        return converter;
    }

}
