package com.yaobanTech.springcloud.config.oauth2;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.nio.charset.Charset;

/**
 * GetSecret (获取Header以及加密后的密码)
 *
 * @author
 * @date 2020-09-04
 */
public class GetSecret {

    /**
     * 对应数据库中的client_id的值
     */
    private static final String APP_KEY = "demo-client";
    /**
     * 对应数据库中的client_secret的值
     */
    private static final String SECRET_KEY = "demo-secret";

    /**
     * main方法执行程序获取到数据库中加密后的client_secret和请求头中的getHeader
     * @param args
     */
    public static void main(String[] args){

        System.out.println("client_secret: "+new BCryptPasswordEncoder().encode(SECRET_KEY));

        System.out.println("getHeader: "+getHeader());

    }

    /**
     * 构造Basic Auth认证头信息
     *
     * @return
     */
    private static String getHeader() {
        String auth = APP_KEY + ":" + SECRET_KEY;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }


}

