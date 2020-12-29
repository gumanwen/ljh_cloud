/*
package com.yaobanTech.springcloud.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

*/
/**
 * ********在实际项目中此资源服务可以单独提取到资源服务项目中使用********
 *
 * OAuth2的资源服务配置类(主要作用是配置资源受保护的OAuth2策略)
 * 注：技术架构通常上将用户与客户端的认证授权服务设计在一个子系统(工程)中,而资源服务设计为另一个子系统(工程)
 *
 * @author
 * @date 2020-09-04
 *//*

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResServerConfig extends ResourceServerConfigurerAdapter{

    @Autowired
    private TokenStore jwtTokenStore;

    */
/**
     * 同认证授权服务配置jwtTokenStore - 单独剥离服务需要开启注释
     * @return
     *//*

//	@Bean
//	public TokenStore jwtTokenStore() {
//		return new JwtTokenStore(jwtAccessTokenConverter());
//	}

    */
/**
     * 同认证授权服务配置jwtAccessTokenConverter  - 单独剥离服务需要开启注释
     * 需要和认证授权服务设置的jwt签名相同: "demo"
     * @return
     *//*

//	@Bean
//	public JwtAccessTokenConverter jwtAccessTokenConverter() {
//		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
//		accessTokenConverter.setSigningKey(Oauth2Constant.JWT_SIGNING_KEY);
//		accessTokenConverter.setVerifierKey(Oauth2Constant.JWT_SIGNING_KEY);
//		return accessTokenConverter;
//	}

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore);
    }

    */
/**
     * 配置受OAuth2保护的URL资源。
     * 注意:必须配置sessionManagement(),否则访问受护资源请求不会被OAuth2的拦截器
     * 		ClientCredentialsTokenEndpointFilter与OAuth2AuthenticationProcessingFilter拦截,
     * 		也就是说,没有配置的话,资源没有受到OAuth2的保护。
     * @param http
     * @throws Exception
     *//*

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	*/
/*
    	 注意：
    	 1、必须先加上：.requestMatchers().antMatchers(...)，表示对资源进行保护，也就是说，在访问前要进行OAuth认证。
    	 2、接着：访问受保护的资源时，要具有哪里权限。
    	 ------------------------------------
    	 否则，请求只是被Security的拦截器拦截，请求根本到不了OAuth2的拦截器。
    	 ------------------------------------
    	 requestMatchers()部分说明：
    	 Invoking requestMatchers() will not override previous invocations of ::
    	 mvcMatcher(String)}, requestMatchers(), antMatcher(String), regexMatcher(String), and requestMatcher(RequestMatcher).
    	 *//*

        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                //另外，如果不设置，那么在通过浏览器访问被保护的任何资源时，每次是不同的SessionID，并且将每次请求的历史都记录在OAuth2Authentication的details的中
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers()
                .antMatchers("/user","/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/user","/api/**")
                .authenticated();
    }
}

*/
