package com.yaobanTech.springcloud.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaobanTech.springcloud.pojos.*;
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

    @Value("${qyserver.userAdd}")
    private String qyuserIP;

    @Value("${qyserver.roleAdd}")
    private String roleIP;

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return loginUser;
        }
    //根据英文获取中文
    public String getNameByuser(String username,String token){
        UserVo u = new UserVo();
        try {

            // 1. 得到访问地址的URL
            URL url = new URL(String.valueOf(qyuserIP)+"?loginname="+username);
            // 2. 得到网络访问对象java.net.HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("GET");
            connection.setRequestProperty("TW-Authorization",token);
            connection.setRequestProperty("content-type","application/json;charset=UTF-8");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置超时时间
            connection.setConnectTimeout(3000);

            /*OutputStream outputStream = connection.getOutputStream();
            String data = "loginname="+username;//拼装参数
            outputStream.write(data.getBytes());*/

            /*StringBuilder params = new StringBuilder();
            String urlEncodedIds= URLEncoder.encode(username, "utf-8");
            params.append("loginname").append("=").append(username);
            byte[] bytes = params.toString().getBytes();
            connection.getOutputStream().write(bytes);*/
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
            // 6. 断开连接，释放资源
            connection.disconnect();
            System.out.println("MSG:"+msg);
            QyRespBean resp =  JSON.parseObject(msg,QyRespBean.class);
            System.out.println("resp:"+resp.getData().toString());
            if(FieldUtils.isObjectNotEmpty(resp)){
                ObjectMapper mapper = new ObjectMapper();
                String jsonStr = String.valueOf((JSON.parseObject(String.valueOf(resp.getData()), Map.class)).get("data"));
                List<UserVo> list = JSON.parseArray(jsonStr, UserVo.class);
                if(list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        if(username.equals(list.get(i).getLoginname())){
                            u = list.get(i);
                        }
                    }
                }
                if(FieldUtils.isObjectNotEmpty(u)){
                    System.out.println("平台接口中文名称："+u.getName());
                    return u.getName();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u.getName();
    }
    //根据角色获取用户列表
    public List<User>  selectUserByRole(String role,String dept,String token){

        List<User> result = new ArrayList<>();
        List<UserVo> list = new ArrayList<>();
        try {
            // 1. 得到访问地址的URL
            URL url = new URL(String.valueOf(roleIP)+"?roleIds="+role+"&deptIds="+dept);
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
            connection.setRequestProperty("content-type","application/json;charset=UTF-8");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置超时时间
            connection.setConnectTimeout(3000);
            //添加参数
            /*OutputStream outputStream = connection.getOutputStream();
            String data = "roleIds="+"64";//拼装参数
            outputStream.write(data.getBytes());*/
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
            // 6. 断开连接，释放资源
            connection.disconnect();
            QyRespBean resp =  JSON.parseObject(msg,QyRespBean.class);
            System.out.println("resp："+resp);
            if(FieldUtils.isObjectNotEmpty(resp)){
                list = JSON.parseArray(String.valueOf(resp.getData()),UserVo.class);
                if(list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        User n = new User();
                        n.setId(list.get(i).getId());
                        n.setName(list.get(i).getName());
                        n.setUsername(list.get(i).getLoginname());
                        System.out.println("平台接口对象："+n);
                        result.add(n);
                    }
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //根据角色获取用户列表
    public List<User>  selectAllUserByRole(String token){

        List<User> result = new ArrayList<>();
        List<UserVo> list = new ArrayList<>();
        try {
            // 1. 得到访问地址的URL
            URL url = new URL(String.valueOf(roleIP)+"?roleIds=63,64");
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
            connection.setRequestProperty("content-type","application/json;charset=UTF-8");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置超时时间
            connection.setConnectTimeout(3000);
            //添加参数
            /*OutputStream outputStream = connection.getOutputStream();
            String data = "roleIds="+"64";//拼装参数
            outputStream.write(data.getBytes());*/
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
            // 6. 断开连接，释放资源
            connection.disconnect();
            QyRespBean resp =  JSON.parseObject(msg,QyRespBean.class);
            System.out.println("resp："+resp);
            if(FieldUtils.isObjectNotEmpty(resp)){
                list = JSON.parseArray(String.valueOf(resp.getData()),UserVo.class);
                if(list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        User n = new User();
                        n.setName(list.get(i).getName());
                        n.setUsername(list.get(i).getLoginname());
                        System.out.println("平台接口对象："+n);
                        result.add(n);
                    }
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
