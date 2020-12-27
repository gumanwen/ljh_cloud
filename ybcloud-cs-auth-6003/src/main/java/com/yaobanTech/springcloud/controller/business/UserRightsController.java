package com.yaobanTech.springcloud.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.pojos.RespBean;
import com.yaobanTech.springcloud.service.UserRightsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Api(value = "权限Controller" , tags = "用户权限--接口")
@RestController
@RequestMapping("/api/userRight")
public class UserRightsController {

    @Autowired
    private UserRightsService userRightsService;

    @ApiOperation(value ="获取组织架构")
    @GetMapping("getOrg")
    public RespBean getOrg(){
        return userRightsService.getOrg();
    }

    @ApiOperation(value="新增角色")
    @PostMapping("newRole")
    public RespBean newRole(@RequestBody Map<String,Object> params){
        return userRightsService.newRole(params);
    }

    @ApiOperation(value ="修改角色")
    @PostMapping("updateRole")
    public RespBean updateRole(@RequestBody  Map<String,Object> params){
        return userRightsService.updateRole(params);
    }

    @ApiOperation(value="删除角色")
    @PostMapping("modifyRole")
    public RespBean modifyRole(@RequestParam String id){
        return userRightsService.deleteRole(id);
    }

    @ApiOperation(value="获取用户可选角色")
    @GetMapping("getRoleByuser")
    public RespBean getRoleByuser(){
        return userRightsService.getRoleByuser();
    }

    @ApiOperation(value="获取用户可选组织")
    @GetMapping("getOrgByUser")
    public RespBean getOrgByUser(){
        return userRightsService.getOrgByUser();
    }

    @ApiOperation(value ="获取全部角色列表")
    @GetMapping("getAllRolesList")
    public RespBean getAllRolesList(){
        return  userRightsService.getAllRolesList();
    }

    @ApiOperation(value="删除用户")
    @PostMapping("deleteUser")
    public RespBean deleteUser(@RequestParam String id ){
        return userRightsService.deleteUser(id);
    }

    @ApiOperation(value ="修改用户")
    @PostMapping("modifyUser")
    public RespBean modifyUser(@RequestBody Map<String,Object> params){
        Map jsonMap = (Map) JSONObject.parse((String)params.get("form"));
        return  userRightsService.modifyUser(jsonMap);
    }

    @ApiOperation(value ="新增用户")
    @PostMapping("insertUser")
    public RespBean insertUser(@RequestBody Map<String,Object> params){
        Map jsonMap = (Map) JSONObject.parse((String)params.get("form"));
        return userRightsService.newUser(jsonMap);
    }

    @ApiOperation(value ="获取全部用户列表")
    @GetMapping("getAllUsersList")
    public RespBean getAllUsersList(String department){
        return userRightsService.getAllUsersList(department);
    }

    @ApiOperation(value="新增菜单")
    @PostMapping("newFirstLevelMenu")
    public RespBean newFirstLevelMenu(@RequestBody Map<String,Object> params){
        return userRightsService.newFirstLevelMenu(params);
    }

    @ApiOperation(value="修改菜单")
    @PostMapping("modifyFirstLevelMenu")
    public RespBean modifyFirstLevelMenu(@RequestBody Map<String,Object> params){
        return userRightsService.modifyFirstLevelMenu(params);
    }

    @ApiOperation(value="删除菜单")
    @PostMapping("deleteFirstLevelMenu")
    public RespBean deleteFirstLevelMenu(@RequestParam String id){
        return userRightsService.deleteFirstLevelMenu(id);
    }

    @ApiOperation(value ="获取所有菜单列表")
    @GetMapping("getMenus")
    public RespBean getMenus(){
        return userRightsService.getMenus();
    }

    @ApiOperation(value ="获取该用户的菜单")
    @GetMapping("getMenusByRole")
    public RespBean getMenusByRole(@RequestParam  String name){
        return userRightsService.getMenusByRole(name);
    }
}
