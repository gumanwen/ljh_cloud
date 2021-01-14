package com.yaobanTech.springcloud.config.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * OAuth2的授权服务配置:主要作用是OAuth2的客户端进行认证与授权
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    @Qualifier("myUserDetailsService")
    public UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    /**
     * 配置OAuth2的客户端信息：clientId、client_secret、authorization_type、redirect_url等。
     * 实际保存在数据库中 ：OAUTH_CLIENT_DETAILS
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * 1.增加jwt 增强模式
     * 2.调用userDetailsService实现UserDetailsService接口,对客户端信息进行认证与授权
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * jwt 增强模式
         * 对令牌的增强操作就在enhance方法中
         * 下面在配置类中,将TokenEnhancer和JwtAccessConverter加到一个enhancerChain中
         *
         * 通俗点讲它做了两件事：
         * 给JWT令牌中设置附加信息和jti：jwt的唯一身份标识,主要用来作为一次性token,从而回避重放攻击
         * 判断请求中是否有refreshToken,如果有,就重新设置refreshToken并加入附加信息
         */
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<TokenEnhancer>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList); //将自定义Enhancer加入EnhancerChain的delegates数组中
        endpoints.tokenStore(jwtTokenStore)
                .userDetailsService(userDetailsService)
                /**
                 * 支持 password 模式
                 */
                .authenticationManager(authenticationManager)
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); //设置允许get,post

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }
}

