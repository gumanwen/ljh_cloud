package com.yaobanTech.springcloud.mapper;

import com.yaobanTech.springcloud.pojos.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {

 @Insert("insert into SEC_USER (name,username,password,role) values(#{name},#{username},#{password},#{role})")
 public void save(User user);

 User loadUserByUsername(String username);

 void newRole(Map jsonMap);

 int selectZJ();

 void insertRoleAndMenu(@Param("temp") List<HashMap<String, Object>> temp);

 void modifyRole(Map jsonMap);

 void deleteMenuByRole(String role_id);

 void insertUser(Map jsonMap);

 int selectUserZJ();

 void insertUserAndRole(@Param("temp")List<HashMap<String, Object>> temp);

 List<HashMap<Object, Object>> selectRoleByUser(String id);

 void deleteUser(String id);

 void deleteUserAndRole(String id);

 List<HashMap<Object, Object>> getAllOrgs();

 void insertUserAndOrg(@Param("orgtemp")List<HashMap<String, Object>> orgtemp);

 List<HashMap<Object, Object>> selectOrgByUser(String id);

 List<HashMap<Object, Object>> selectUserByDept(String department);

 void modifyUser(Map jsonMap);

 List<HashMap<Object, Object>> getUserMenusByRole(String name,String grade);

 List<HashMap<String, Object>> selectRoleCodeByUser(int user_id);

 List<HashMap<String, Object>> selectRoleByRoleId(String id);

 void deleteRole(String id);

 void deleteRoleAndMenu(String id);

 List<HashMap<Object, Object>> getOrgList();

 List<HashMap<Object, Object>> getSecondOrgList(String  id);

 List<HashMap<Object, Object>>  getAllRoles();

 List<HashMap<Object, Object>> selectRoles();

 List<HashMap<Object, Object>> selectMenuByRole(String id);

 void newFirstLevelMenu(Map jsonMap);

 void modifyFirstLevelMenu(Map jsonMap);

 void deleteFirstLevelMenu(String id);

 List<HashMap<Object, Object>> getMenuList(String grade);

 List<HashMap<String,String>> selectUserByRole(String role);
}
