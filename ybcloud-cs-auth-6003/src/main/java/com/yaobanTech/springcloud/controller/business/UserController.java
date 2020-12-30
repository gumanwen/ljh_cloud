package com.yaobanTech.springcloud.controller.business;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yaobanTech.springcloud.pojos.JwtUser;
import com.yaobanTech.springcloud.pojos.RespBean;
import com.yaobanTech.springcloud.pojos.User;
import com.yaobanTech.springcloud.service.UserRightsService;
import com.yaobanTech.springcloud.service.UserService;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务接口
 *
 */

@Api(value = "权限Controller" , tags = "用户权限--接口")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRightsService userRightsService;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    TokenEndpoint tokenEndPoint;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token =  StringUtils.substringAfter(header, "Bearer ");
        Map map= Jwts.parser()
        .setSigningKey("ybcloud".getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody();
        return  RespBean.ok("").setObj(map.get("user_name"));
    }

    /*@PostMapping("/login")
    public RespBean login(@RequestBody Map<String,String> param) throws HttpRequestMethodNotSupportedException {
        return userRightsService.login(param);
    }*/

    @PostMapping("/login")
    public RespBean getToken(@RequestBody Map<String,String> param) {
        //1.申请令牌
        String authUrl = "http://192.168.50.167:6003/oauth/token";
        String username = param.get("username");
        String password = param.get("password");
        String grant_type ="password";
        String scope ="all";
        String client_id ="demo-client";
        String client_secret ="demo-secret";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", this.getHttpBasic(client_id, client_secret));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        ResponseEntity<Map> responseEntity = restTemplate.exchange(authUrl, HttpMethod.POST, requestEntity, Map.class);
        Map map = responseEntity.getBody();
        if (map == null || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null) {
            //申请令牌失败
           // ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        //2.封装结果数据
        //AuthToken authToken = new AuthToken().setAccessToken((String) map.get("access_token")).setRefreshToken((String) map.get("refresh_token")).setJwtToken((String) map.get("jti"));
        //3.将jti作为redis中的key,将jwt作为redis中的value进行数据的存放
        //stringRedisTemplate.boundValueOps(authToken.getJwtToken()).set(authToken.getAccessToken(), ttl, TimeUnit.SECONDS);
        HashMap<String,Object> result = new HashMap<>();
        JwtUser u = userService.loadUserByUsername(username);
        Iterator iterator = u.getAuthorities().iterator();
        Object object =null;
        while(iterator.hasNext()) {
            object = iterator.next();
            //这里的Object就是你专的集合里的数据类型，不知道可以属object.getClass看看
        }
        result.put("username",username);
        result.put("userId",u.getId());
        result.put("roles", String.valueOf(object));
        result.put("token", map.get("access_token"));
        return RespBean.ok("登录成功！").setObj(result);
    }
    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic " + new String(encode);
    }
}

