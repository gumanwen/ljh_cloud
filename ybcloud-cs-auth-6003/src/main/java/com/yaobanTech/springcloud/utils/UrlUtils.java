package com.yaobanTech.springcloud.utils;

import com.alibaba.fastjson.JSON;
import com.yaobanTech.springcloud.pojos.LoginUser;
import com.yaobanTech.springcloud.pojos.QyRespBean;
import com.yaobanTech.springcloud.pojos.Role;
import com.yaobanTech.springcloud.pojos.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class UrlUtils {

    @Value("${qyserver.ipAdd}")
    private String qyIP;
    /*
    * 获取清远平台当前登录人信息
    */
    public Object getCurrentUserInfo(String token){
        URL url;
        try {//119.135.180.178
            url = new URL("http://218.17.45.99:8850/api/auth/user/get");
            OutputStreamWriter out = null;
            BufferedReader br = null;
            StringBuffer result = new StringBuffer("");
            // 打开URL连接
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("TW-Authorization","60701A2DBA05467C97A236E9CBEF5E2F");
            /*设置通用请求属性*/
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("user-agent", "PostmanRuntime/7.26.8");
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            con.setDoOutput(true);
            con.setDoInput(true);

            out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            out.flush();
            /*获取输入流*/
            InputStream inputStream = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str = "";
            while ((str = br.readLine()) != null) {
                result.append(str);
            }
            String resultStr = result.toString();
            System.out.printf("__-----"+resultStr);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return "";
    }

    public LoginUser getMsg(String token){
        UserInfo u = new UserInfo();
        LoginUser loginUser =new LoginUser();
                try {
                    // 1. 得到访问地址的URL
                    URL url = new URL(String.valueOf(qyIP));
                    // 2. 得到网络访问对象java.net.HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
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
                                new InputStreamReader(connection.getInputStream()));
                        String line = null;

                        while ((line = reader.readLine()) != null) { // 循环从流中读取
                            msg += line + "\n";
                        }
                        reader.close(); // 关闭流
                    }
                    // 6. 断开连接，释放资源
                    connection.disconnect();

                    QyRespBean resp =  JSON.parseObject(msg,QyRespBean.class);
                    if(FieldUtils.isObjectNotEmpty(resp)){
                        u = JSON.parseObject(String.valueOf(resp.getData()),UserInfo.class);
                        List<Role> roles = u.getRoles();
                        String role = null;
                        if(roles.size()>0){
                            for (int i = 0; i < roles.size(); i++) {
                                role =role+ roles.get(i).toString()+",";
                            }
                        }
                        loginUser.setRoleLists(role);
                        loginUser.setName(u.getName());
                        loginUser.setLoginname(u.getLoginname());
                        u.setRoleslist(role);
                        if(FieldUtils.isObjectNotEmpty(loginUser)){
                            return loginUser;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return loginUser;
        }


    /*public static void main(String[] args) {
        getCurrentUserInfo("");
        getMsg("");
    }*/
}
