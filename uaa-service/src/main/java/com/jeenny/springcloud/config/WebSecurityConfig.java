package com.jeenny.springcloud.config;

import com.jeenny.springcloud.serviceimpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2019/4/15.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return  super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request,response,authException)->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests().antMatchers(
                        "/oauth/authorize","/oauth/confirm_access")
                .permitAll()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
                .and()
                .authorizeRequests().anyRequest().hasRole("CLIENT")
        ;
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
