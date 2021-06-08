package com.yaobanTech.springcloud.config.oauth2;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaobanTech.springcloud.pojos.*;
import com.yaobanTech.springcloud.utils.FieldUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UrlUtils {

    @Value("${qyserver.ipAdd}")
    private String qyIP;

    public LoginUser getMsg(String token){
        UserInfo u = new UserInfo();
        LoginUser loginUser =new LoginUser();
                try {
                    // 1. 得到访问地址的URL
                    URL url = new URL(String.valueOf(qyIP));
                    // 2. 得到网络访问对象java.net.HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
                    // 设置是否向HttpURLConnection输出
                    connection.setDoOutput(false);
                    // 设置是否从httpUrlConnection读入
                    connection.setDoInput(true);
                    // 设置请求方式
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("TW-Authorization",token);
                    // 设置是否使用缓存
                    connection.setUseCaches(true);
                    // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                    connection.setInstanceFollowRedirects(true);
                    // 设置超时时间
                    connection.setConnectTimeout(3000);
                    // 连接
                    connection.connect();
                    // 4. 得到响应状态码的返回值 responseCode
                    int code = connection.getResponseCode();
                    // 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
                    String msg = "";
                    if (code == 200) { // 正常响应
                        // 从流中读取响应信息
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream(),"UTF-8"));
                        String line = null;

                        while ((line = reader.readLine()) != null) { // 循环从流中读取
                            msg += line + "\n";
                        }
                        reader.close(); // 关闭流
                    }
                    System.out.println("当前用户的code是:"+code);
                    // 6. 断开连接，释放资源
                    connection.disconnect();
                    QyRespBean resp =  JSON.parseObject(msg,QyRespBean.class);
                    if(!msg.contains("20001")){
                        if(FieldUtils.isObjectNotEmpty(resp)){
                            u = JSON.parseObject(String.valueOf(resp.getData()),UserInfo.class);
                            List<Role> roles = u.getRoles();
                            Dept dept= u.getDept();
                            String role = null;
                            //如果有id为63的角色，判定为巡查队长
                            loginUser.setRoleLists("ROLE_BZY");
                            if(roles.size()>0){
                                for (int i = 0; i < roles.size(); i++) {
                                    if(roles.get(i).getId() == 63){
                                        loginUser.setRoleLists("ROLE_BZZ");
                                    }
                                }
                            }
                            loginUser.setDeptName(dept.getName());
                            loginUser.setName(u.getName());
                            loginUser.setLoginname(u.getLoginname());
                            System.out.println("该用户的角色是："+loginUser.getRoleLists());
                            u.setRoleslist(role);
                            if(FieldUtils.isObjectNotEmpty(loginUser)){
                                return loginUser;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        return loginUser;
    }
}
