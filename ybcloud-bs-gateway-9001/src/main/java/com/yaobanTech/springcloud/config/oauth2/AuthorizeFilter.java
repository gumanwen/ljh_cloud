package com.yaobanTech.springcloud.config.oauth2;

import com.yaobanTech.springcloud.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
 * 鉴权过滤器 验证token
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String QY_AUTHORIZE_TOKEN = "TW-Authorization";
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2. 则获取响应
        ServerHttpResponse response = exchange.getResponse();
        //3. 如果是登录请求则放行
        if (request.getURI().getPath().contains("/user") || request.getURI().getPath().contains("/oauth") || request.getURI().getPath().contains("/file")) {
            return chain.filter(exchange);
        }
        //4. 获取请求头
        HttpHeaders headers = request.getHeaders();
        //5. 请求头中获取令牌
        Boolean flag = false;
        String qytoken = headers.getFirst(QY_AUTHORIZE_TOKEN);
        logger.info("qytoken："+qytoken+"++++++++++++++++++++++++++++++++++");
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        token =  StringUtils.substringAfter(token, "Bearer ");
        //6. 判断请求头中是否有令牌
        if (StringUtils.isEmpty(token)) {
            flag =false;
        }else{
            //9. 如果请求头中有令牌则解析令牌
            try {
                JwtUtil.parseJWT(token);
                flag =true;
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(StringUtils.isNotEmpty(qytoken)){
            flag = true;
        }
        if(flag){
            //12. 放行
            return chain.filter(exchange);
        }else{
            //7. 响应中放入返回的状态吗, 没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //8. 返回
            return response.setComplete();
        }
    }
    @Override
    public int getOrder() {
        return 0;
    }
}


