package com.yaobanTech.springcloud.config.oauth2.jwt;
import com.yaobanTech.springcloud.pojos.Oauth2Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 使用Jwt存储token的配置
 */
@Configuration
public class JwtTokenConfig {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * JwtAccessTokenConverter：TokenEnhancer的子类,帮助程序在JWT编码的令牌值和OAuth身份验证信息之间进行转换(在两个方向上),同时充当TokenEnhancer授予令牌的时间。
     * 自定义的JwtAccessTokenConverter：把自己设置的jwt签名加入accessTokenConverter中(这里设置'demo',项目可将此在配置文件设置)
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(Oauth2Constant.JWT_SIGNING_KEY);
        return accessTokenConverter;
    }

    /**
     * 引入自定义JWTokenEnhancer:
     * 自定义JWTokenEnhancer实现TokenEnhancer并重写enhance方法,将附加信息加入oAuth2AccessToken中
     * @return
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer(){
        return new JWTokenEnhancer();
    }
}


