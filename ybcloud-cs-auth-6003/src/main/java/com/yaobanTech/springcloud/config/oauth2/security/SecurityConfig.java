package com.yaobanTech.springcloud.config.oauth2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity /*启用springsecurity*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 引入密码加密类
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 支持 password 模式(配置)
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置URL访问授权,必须配置authorizeRequests(),否则启动报错,说是没有启用security技术。
     * 注意:在这里的身份进行认证与授权没有涉及到OAuth的技术：当访问要授权的URL时,请求会被DelegatingFilterProxy拦截,
     *      如果还没有授权,请求就会被重定向到登录界面。在登录成功(身份认证并授权)后,请求被重定向至之前访问的URL。
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf() //防止CSRF（跨站请求伪造）配置
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/token"))
                .disable()
                .authorizeRequests().antMatchers("/","/home","/user").permitAll(); //不用身份认证可以访问

    }

}
