package com.yaobanTech.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.mapper.UserMapper;
import com.yaobanTech.springcloud.pojos.RespBean;
import com.yaobanTech.springcloud.utils.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRightsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    TokenEndpoint tokenEndPoint;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public RespBean newRole(Map<String,Object> params) {
        Map jsonMap = (Map) JSONObject.parse((String)params.get("form"));
        if(FieldUtils.isObjectNotEmpty(jsonMap.get("role_name"))){
            userMapper.newRole(jsonMap);
            int role_id = userMapper.selectZJ();
            List<String> menuList = new ArrayList<String>();
            List<HashMap<String,Object>> temp = new ArrayList<HashMap<String,Object>>();

            menuList = (List<String>) jsonMap.get("menuList");
            if(menuList.size()>0){
                for(int i=0;i<menuList.size();i++){
                    HashMap<String,Object> tempMap = new HashMap<String,Object>();
                    tempMap.put("menu_id",menuList.get(i));
                    tempMap.put("role_id",role_id);
                    temp.add(tempMap);
                }
                userMapper.insertRoleAndMenu(temp);
            }
            return RespBean.ok("新增成功！");
        }else{
            return  RespBean.error("缺少名称！");
        }
    }
    @Transactional
    public RespBean updateRole( Map<String,Object> params) {
        Map jsonMap = (Map) JSONObject.parse((String)params.get("form"));
        String role_id = String.valueOf(jsonMap.get ("id"));
        if(FieldUtils.isStringNotEmpty(role_id)){
            userMapper.modifyRole(jsonMap);
            List<String> menuList = new ArrayList<String>();
            List<HashMap<String,Object>> temp = new ArrayList<HashMap<String,Object>>();
            //删除原来的关系
            userMapper.deleteMenuByRole(role_id);
            menuList = (List<String>) jsonMap.get("menuList");
            if(menuList.size()>0){
                for(int i=0;i<menuList.size();i++){
                    HashMap<String,Object> tempMap = new HashMap<String,Object>();
                    tempMap.put("menu_id",menuList.get(i));
                    tempMap.put("role_id",role_id);
                    temp.add(tempMap);
                }
                userMapper.insertRoleAndMenu(temp);
            }
            return RespBean.ok("新增成功！");
        }else{
            return RespBean.error("缺少主键！");
        }
    }

    @Transactional
    public RespBean newUser(Map jsonMap) {
        jsonMap.put("password",bCryptPasswordEncoder.encode((CharSequence) jsonMap.get("password")));
        userMapper.insertUser(jsonMap);
        int user_id = userMapper.selectUserZJ();
        List<String> roleList = new ArrayList<String>();
        List<String> orgList = new ArrayList<String>();
        roleList = (List<String>) jsonMap.get("role");
        orgList = (List<String>) jsonMap.get("department");
        List<HashMap<String,Object>> temp = new ArrayList<HashMap<String,Object>>();
        List<HashMap<String,Object>> orgtemp = new ArrayList<HashMap<String,Object>>();
        if(roleList.size()>0){
            for(int i=0;i<roleList.size();i++){
                HashMap<String,Object> tempMap = new HashMap<String,Object>();
                tempMap.put("role_id",roleList.get(i));
                tempMap.put("user_id",user_id);
                temp.add(tempMap);
            }
            userMapper.insertUserAndRole(temp);
        }
        if(orgList.size()>0){
            for(int i=0;i<orgList.size();i++){
                HashMap<String,Object> tempMap = new HashMap<String,Object>();
                tempMap.put("org_id",orgList.get(i));
                tempMap.put("user_id",user_id);
                orgtemp.add(tempMap);
                userMapper.insertUserAndOrg(orgtemp);
            }
        }
        //获取role_code
        List<HashMap<String,Object>> roleCode= userMapper.selectRoleCodeByUser(user_id);
        String role="";
        if(roleCode.size()>0){
            for(int i=0;i<roleCode.size();i++){
                if(i<roleCode.size()-1){
                    role += roleCode.get(i).get("role_code")+",";
                }else{
                    role += roleCode.get(i).get("role_code");
                }
            }
        }
        jsonMap.put("id",user_id);
        jsonMap.put("role",role);
        userMapper.modifyUser(jsonMap);
        return RespBean.ok("新增成功！");
    }

    @Transactional
    public RespBean deleteRole(String id) {
        if(FieldUtils.isStringNotEmpty(id)){
            userMapper.deleteRole(id);
            userMapper.deleteRoleAndMenu(id);
            return RespBean.ok("删除成功！");
        }else{
            return  RespBean.error("缺少主键");
        }
    }
    @Transactional
    public RespBean deleteUser(String id) {
        if(FieldUtils.isStringNotEmpty(id)){
            userMapper.deleteUser(id);
            userMapper.deleteUserAndRole(id);
            return RespBean.ok("删除成功！");
        }else{
            return RespBean.error("缺少主键");
        }
    }
    @Transactional
    public RespBean modifyUser(Map jsonMap) {
        String user_id = String.valueOf(jsonMap.get("id"));
        if(FieldUtils.isObjectNotEmpty(jsonMap.get("id"))) {
            List<String> roleList = new ArrayList<String>();
            String rolesList = "";
            List<HashMap<String, Object>> roletemp = new ArrayList<HashMap<String, Object>>();
            roleList = (List<String>) jsonMap.get("role");
            if(roleList.size()>0){
                for(int i=0;i<roleList.size();i++){
                    roletemp =userMapper.selectRoleByRoleId(String.valueOf(roleList.get(i)));
                    if(roletemp.size()>0){
                        if(i<roleList.size()-1){
                            rolesList += (String) roletemp.get(0).get("role_code")+",";
                        }else{
                            rolesList += (String) roletemp.get(0).get("role_code");
                        }
                    }else{
                        rolesList="1";
                    }
                }
            }
            jsonMap.put("role",rolesList);
            userMapper.modifyUser(jsonMap);
            List<String> orgList = new ArrayList<String>();
            orgList = (List<String>) jsonMap.get("department");
            List<HashMap<String, Object>> temp = new ArrayList<HashMap<String, Object>>();
            List<HashMap<String, Object>> orgtemp = new ArrayList<HashMap<String, Object>>();
            //获取原来的关系
            List<HashMap<Object, Object>> allOrgList= userMapper.selectOrgByUser(user_id);
            List<HashMap<Object, Object>> allRoleList= userMapper.selectRoleByUser(user_id);
            //重新构建关系
            if (roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    if(allRoleList.size()>0){
                        for(int j=0;j<allRoleList.size();j++){
                            if(!allRoleList.get(j).get("id").equals(roleList.get(i))){
                                HashMap<String, Object> tempMap = new HashMap<String, Object>();
                                tempMap.put("role_id", roleList.get(i));
                                tempMap.put("user_id", user_id);
                                temp.add(tempMap);
                            }
                        }
                    }
                }
                if(temp.size()>0){
                    userMapper.insertUserAndRole(temp);
                }
            }
            if (orgList.size() > 0) {
                for (int i = 0; i < orgList.size(); i++) {
                    if(allOrgList.size()>0){
                        for(int j=0;j<allOrgList.size();j++){
                           if(!allOrgList.get(j).get("id").equals(orgList.get(i))){
                               HashMap<String, Object> tempMap = new HashMap<String, Object>();
                               tempMap.put("org_id", orgList.get(i));
                               tempMap.put("user_id", user_id);
                               orgtemp.add(tempMap);
                           }
                        }
                    }

                }
                if(orgtemp.size()>0){
                    userMapper.insertUserAndOrg(orgtemp);
                }
            }
            return RespBean.ok("修改成功！");
        }else{
            return RespBean.error("缺少主键！");
        }
    }
    @Transactional
    public RespBean getMenusByRole(String name){
        List<HashMap<Object,Object>> firstMenuList = userMapper.getUserMenusByRole(name,"1");
        List<HashMap<Object,Object>> secondMenuList = userMapper.getUserMenusByRole(name,"2");
        List<HashMap<Object,Object>> thirdMenuList = userMapper.getUserMenusByRole(name,"3");
        HashMap<String,Object> result = new HashMap<String,Object>();
        if(firstMenuList.size()>0){
            for(int i=0;i<firstMenuList.size();i++){
                List<HashMap<Object,Object>> secondtemp = new ArrayList<HashMap<Object,Object>>();
                if(secondMenuList.size()>0){

                    for(int j=0;j<secondMenuList.size();j++){
                        List<HashMap<Object,Object>> temp = new ArrayList<HashMap<Object,Object>>();
                        if(thirdMenuList.size()>0){
                            for(int z=0;z<thirdMenuList.size();z++){
                                if(secondMenuList.get(j).get("id").equals(thirdMenuList.get(z).get("pid"))){
                                    temp.add(thirdMenuList.get(z));
                                }
                            }
                        }
                        if(firstMenuList.get(i).get("id").equals(secondMenuList.get(j).get("pid"))){
                            secondtemp.add(secondMenuList.get(j));
                        }
                        if(temp.size()>0){
                            secondMenuList.get(j).put("children",temp);
                        }
                    }
                }
                result.put((String) firstMenuList.get(i).get("code"),secondtemp);
            }
        }
        return RespBean.ok("该用户可以看到的菜单！").setObj(result);
    }

    @Transactional
    public RespBean getOrg(){
        List<HashMap<Object, Object>> orgList = userMapper.getOrgList();
        List<HashMap<Object, Object>> secondOrgList = new ArrayList<HashMap<Object, Object>>();
        if(orgList.size()>0){
            for(int i=0 ; i<orgList.size() ; i++){
                secondOrgList = userMapper.getSecondOrgList(String.valueOf(orgList.get(i).get("id")));
                orgList.get(i).put("item",secondOrgList);
            }
        }
        return RespBean.ok("组织架构列表").setObj(orgList);
    }

    @Transactional
    public RespBean getRoleByuser(){
        return  RespBean.ok("可选角色").setObj(userMapper.getAllRoles());
    }
    @Transactional
    public RespBean getOrgByUser(){
        return RespBean.ok("可选组织").setObj(userMapper.getAllOrgs());
    }
    @Transactional
    public RespBean getAllRolesList(){
        List<HashMap<Object, Object>> roleList= userMapper.selectRoles();
        if(roleList.size()>0){
            for(int i=0;i<roleList.size();i++){
                List<HashMap<Object, Object>> menusList= userMapper.selectMenuByRole(String.valueOf(roleList.get(i).get("id")));
                String menus ="";
                List<Integer> ids = new ArrayList<>();
                if(menusList.size()>0){
                    for(int j=0;j<menusList.size();j++){
                        menus += menusList.get(j).get("name")+" ";
                        ids.add(Integer.valueOf((Integer) menusList.get(j).get("id")));
                    }
                }
                roleList.get(i).put("menuIdList",ids);
                roleList.get(i).put("menuList",menus);
            }
        }
        return RespBean.ok("角色列表").setObj(roleList);
    }
    @Transactional
    public RespBean getAllUsersList(String department){
        List<HashMap<Object, Object>> userList= userMapper.selectUserByDept(department);
        //解密
        if(userList.size()>0){
            for(int i=0;i<userList.size();i++){
                List<HashMap<Object, Object>> rolesList= userMapper.selectRoleByUser(String.valueOf(userList.get(i).get("id")));
                List<HashMap<String,String>> roles = new ArrayList<HashMap<String,String>>();
                if(rolesList.size()>0){
                    for(int j=0;j<rolesList.size();j++){
                        HashMap<String,String> role =new HashMap<>();
                        role.put("id",String.valueOf(rolesList.get(j).get("id")));
                        role.put("role_name",String.valueOf(rolesList.get(j).get("role_name")));
                        roles.add(role);
                    }
                }
                userList.get(i).put("role",roles);
                List<HashMap<Object, Object>> orgsList= userMapper.selectOrgByUser(String.valueOf(userList.get(i).get("id")));
                List<HashMap<String,String>> orgs = new ArrayList<HashMap<String,String>>();
                if(orgsList.size()>0){
                    for(int j=0;j<orgsList.size();j++){
                        HashMap<String,String> org =new HashMap<>();
                        org.put("id",String.valueOf( orgsList.get(j).get("id")));
                        org.put("org_name",String.valueOf( orgsList.get(j).get("org_name")));
                        orgs.add(org);
                    }
                }
                userList.get(i).put("department",orgs);
            }
        }
        return RespBean.ok("用户列表").setObj(userList);
    }
    @Transactional
    public RespBean newFirstLevelMenu( Map<String,Object> params){
        Map jsonMap = (Map) JSONObject.parse((String)params.get("form"));
        if(FieldUtils.isObjectNotEmpty(jsonMap.get("name"))){
            userMapper.newFirstLevelMenu(jsonMap);
            return RespBean.ok("新增菜单成功！");
        }else{
            return RespBean.error("数据不完整！");
        }
    }
    @Transactional
    public RespBean modifyFirstLevelMenu(Map<String,Object> params){
        Map jsonMap = (Map) JSONObject.parse((String)params.get("form"));
        if(FieldUtils.isObjectNotEmpty(jsonMap.get("id"))){
            userMapper.modifyFirstLevelMenu(jsonMap);
            return RespBean.ok("修改菜单成功！");
        }else{
            return RespBean.error("缺少主键！");
        }
    }
    @Transactional
    public RespBean deleteFirstLevelMenu(String id){
        if(FieldUtils.isStringNotEmpty(id)){
            userMapper.deleteFirstLevelMenu(id);
            return RespBean.ok("删除菜单成功！");
        }else{
            return RespBean.error("缺少主键！");
        }
    }
    @Transactional
    public RespBean getMenus(){
        List<HashMap<Object, Object>> firstMenuList = userMapper.getMenuList("1");
        List<HashMap<Object, Object>> secondMenuList = userMapper.getMenuList("2");
        List<HashMap<Object, Object>> thirdMenuList = userMapper.getMenuList("3");
        if(firstMenuList.size()>0){
            for(int i=0;i<firstMenuList.size();i++){
                List<HashMap<Object, Object>> temp = new ArrayList<HashMap<Object,Object>>();
                if(secondMenuList.size()>0){
                    for(int j=0;j<secondMenuList.size();j++){
                        List<HashMap<Object, Object>> children = new ArrayList<HashMap<Object,Object>>();
                        if(firstMenuList.get(i).get("id").equals(secondMenuList.get(j).get("pid"))){
                            if(thirdMenuList.size()>0){
                                for(int z =0;z<thirdMenuList.size();z++){
                                    if(secondMenuList.get(j).get("id").equals(thirdMenuList.get(z).get("pid"))){
                                        thirdMenuList.get(z).put("rowKey",thirdMenuList.get(z).get("id"));
                                        children.add(thirdMenuList.get(z));
                                    }
                                }
                                secondMenuList.get(j).put("rowKey",secondMenuList.get(j).get("id"));
                                secondMenuList.get(j).put("child",children);
                            }
                            temp.add(secondMenuList.get(j));
                        }
                    }
                }
                firstMenuList.get(i).put("rowKey",firstMenuList.get(i).get("id"));
                firstMenuList.get(i).put("item",temp);
            }
        }
        return RespBean.ok("所有菜单").setObj(firstMenuList);
    }

    public RespBean login( Map<String, String> param) throws HttpRequestMethodNotSupportedException {
        Principal principal =null;
        if(FieldUtils.isObjectNotEmpty(param)){
            String username = param.get("username");
            String password = param.get("password");
            String grant_type ="password";
            String scope ="all";
            String client_id ="demo-client";
            String client_secret ="demo-secret";
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("client_id", client_id);
            parameters.put("client_secret", client_secret);
            parameters.put("grant_type", grant_type);
            parameters.put("username", username);
            parameters.put("password", password);
            // 直接调用 /oauth/token 映射的方法，不在通过url调用获取token
            ResponseEntity<OAuth2AccessToken> result = tokenEndPoint.getAccessToken(principal, parameters);
            return RespBean.ok("").setObj(result);
        }else{
            return RespBean.error("缺少账号密码");
        }
    }
}
