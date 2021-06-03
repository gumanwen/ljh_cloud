package com.yaobanTech.springcloud.controller;


import com.yaobanTech.springcloud.entity.Inspect;
import com.yaobanTech.springcloud.entity.Test;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.service.IInspectService;
import com.yaobanTech.springcloud.service.feign.AuthService;
import com.yaobanTech.springcloud.service.feign.PlanService;
import com.yaobanTech.springcloud.service.feign.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
@Api(value = "InspectController" , tags = "巡查任务--接口")
@RestController
@RequestMapping("/api/inspect")
public class InspectController {
    @Resource
    private RouteService routeService;

    @Resource
    private PlanService planService;

    @Resource
    private AuthService authService;

    @Autowired
    private IInspectService iInspectService;

    @ApiOperation("app & web:获取计划任务列表")
    @PostMapping("/plan/getAllInspect")
    public RespBean getPlanInspect(long pageNo,long pageSize,@RequestBody Map<String, Object> params,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        return iInspectService.getPlanInspect(pageNo, pageSize, params, request);
    }

    @ApiOperation("web:根据计划编号获取计划任务列表")
    @GetMapping("/plan/getAllInspectByPlanId")
    public RespBean getAllInspectByPlanId(@RequestParam Integer planId) {
        return iInspectService.getAllInspectByPlanId(planId);
    }

    @ApiOperation("app：获取临时任务列表")
    @GetMapping("/plan/getTempInspect")
    public RespBean getTempInspect(String type) {
        return iInspectService.getTempInspect(type);
    }

    @ApiOperation("web：新增临时任务")
    @GetMapping("/temp/addTempTask")
    public RespBean addTempTask(String waterManagementOffice,Integer routeId,String  routeName ,String inspector,String beginTime,String endTime) throws ParseException {
        return iInspectService.addTempTask(waterManagementOffice,routeId,routeName,inspector,beginTime,endTime);
    }

    @ApiOperation("web：新增计划任务")
    @PostMapping("/temp/addPlanTask")
    public RespBean addPlanTask(String waterManagementOffice,Integer routeId,String  routeName ,Integer planId,String planName,HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        return iInspectService.addPlanTask(waterManagementOffice,routeId,routeName,planId,planName,request);
    }

    @ApiOperation("app & web：获取计划任务详情")
    @GetMapping("/plan/getInspectDetailById")
    public RespBean getInspectDetailById(String inspect_task_id,HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException, ParseException {
        return iInspectService.getInspectDetailById(inspect_task_id,request);
    }

    @ApiOperation("openfeign: openfeign获取任务详情")
    @GetMapping("/plan/getFeignInspectDetailById")
    public RespBean getFeignInspectDetailById(@RequestParam(value = "taskIds") List<String> taskIds){
        return iInspectService.getFeignInspectDetailById(taskIds);
    }

    @ApiOperation("app & web：修改计划任务详情")
    @PostMapping("/plan/updateInspectDetailById")
    public RespBean updateInspectDetailById(@RequestBody Map<String, Object> params) {
        return iInspectService.updateInspectDetailById(params);
    }

    @ApiOperation("app：获取任务的签到点列表")
    @GetMapping("/plan/getCheckInPoints")
    public RespBean getCheckInPoints(Integer routeId,String inspectTaskId) {
        //调用route的rest接口
        return iInspectService.getCheckInPoints(routeId,inspectTaskId);
    }

    @ApiOperation("app：获取签到点详情")
    @GetMapping("/point/getPointDetail")
    public RespBean getPointDetail(Integer id) {
        //调用route的rest接口
        return iInspectService.getPointDetail(id);
    }

    @ApiOperation("app：修改签到点详情")
    @PostMapping("/point/updatePoint")
    public RespBean updatePoint(@RequestBody Map<String, Object> params) {
        //调用route的rest接口
        return iInspectService.updatePoint(params);
    }

    @ApiOperation("openfeign：保存计划之后自动生成任务")
    @PostMapping("/task/autoCreate")
    public RespBean autoCreate(@RequestBody Map<String, Object> params,HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        //调用route的rest接口
        return iInspectService.autoCreate(params,request);
    }

    @ApiOperation("web：派发")
    @PostMapping("/task/send")
    public RespBean send(@RequestBody Map<String, Object> params,HttpServletRequest request) throws UnsupportedEncodingException {
        //调用route的rest接口
        return iInspectService.send(params,request);
    }

    @ApiOperation("web：发起")
    @PostMapping("/task/send1")
    public RespBean send1(HttpServletRequest request) {
        //调用route的rest接口
        return iInspectService.send1(request);
    }

    @ApiOperation("web：获取路线下拉列表")
    @GetMapping("/route/findSelection")
    public RespBean findSelection(@RequestParam("code") String code) {
        return routeService.findSelection(code);
    }

    @ApiOperation("web：获取计划下拉列表")
    @GetMapping("/plan/findSelection")
    public RespBean findplanSelection(@RequestParam("routeId") Integer routeId) {
        return planService.findSelection(routeId);
    }

    @ApiOperation("app: 获取已签到列表")
    @GetMapping("/point/getPointedList")
    public RespBean getPointedList(@RequestParam("routeId") Integer routeId,@RequestParam("inspectTaskId") String inspectTaskId){
        return iInspectService.findSignedList(routeId,inspectTaskId);
    }

    /*已完成的任务不能终止。*/
    @ApiOperation("web: 终止任务")
    @GetMapping("/stop")
    public RespBean stop (String inspectTaskId){
        return iInspectService.stop(inspectTaskId);
    }

    /*任务计划开始时间之前和计划结束时间之后能删除*/
    @ApiOperation("web: 删除任务")
    @GetMapping("/delete")
    public RespBean delete (String inspectTaskId){
        return iInspectService.delete(inspectTaskId);
    }

    @ApiOperation("gis：测试递归")
    @GetMapping("/digui")
    /*@Cacheable("cache1")*/
    public RespBean digui(Integer gid){
        return iInspectService.digui(gid);
    }

    @ApiOperation("gis：生成网络")
    @GetMapping("/create")
    /*@Cacheable("cache1")*/
    public RespBean create(){
        return iInspectService.create();
    }

    @ApiOperation("opnefeign:根据任务开始时间过滤任务编号")
    @GetMapping("/getTaskListByTime")
    public RespBean getTaskListByTime(Date  taskStart1,Date taskEnd1,Date taskStart2,Date taskEnd2,String checkMan,HttpServletRequest request) throws UnsupportedEncodingException {
        return iInspectService.getTaskListByTime(taskStart1,taskEnd1,taskStart2,taskEnd2,checkMan,request);
    }

    @ApiOperation("gis：测试递归")
    @GetMapping("/bijiao")
    public RespBean bijiao(Integer gid){
        return iInspectService.bijiao(gid);
    }

    @ApiOperation("web：巡查统计")
    @GetMapping("/inspectStatistics")
    public RespBean inspectStatistics(String waterManagementOffice,String unit,String beginTime,String deadTime) throws ParseException {
        return iInspectService.inspectStatistics(waterManagementOffice,unit,beginTime,deadTime);
    }

    @ApiOperation("手机：巡查统计")
    @GetMapping("/appInspectStatistics")
    public RespBean appInspectStatistics(HttpServletRequest request) throws UnsupportedEncodingException {
        return iInspectService.appInspectStatistics(request);
    }

    @ApiOperation("根据角色获取用户")
    @GetMapping("/selectUserByRole")
    public RespBean selectUserByRole(String role,HttpServletRequest request) throws UnsupportedEncodingException {
        return iInspectService.selectUserByRole(role,request);
    }
    @ApiOperation("根据角色获取用户")
    @GetMapping("/selectUserByRoleAndDept")
    public RespBean selectUserByRoleAndDept(String role,String dept,HttpServletRequest request) throws UnsupportedEncodingException {
        return iInspectService.selectUserByRoleAndDept(role,dept,request);
    }

    @ApiOperation("/上传GPS坐标")
    @PostMapping("/uploadGPS")
    public RespBean uploadGPS(@RequestBody Map<String, Object> params){
        return iInspectService.uploadGPS(params);
    }

    @ApiOperation("/关阀分析")
    @GetMapping("/getcloseValues")
    public RespBean getcloseValues(String gid){
        return iInspectService.getcloseValues(gid);
    }

    @ApiOperation("openfeign: 路线是否可修改")
    @GetMapping("/isModifiable")
    public Object isModifiable(Integer routeId) {
        return iInspectService.isModifiable(routeId);
    }

    @ApiOperation("/上传设备坐标")
    @PostMapping("/uploadDeviceGPS")
    public RespBean uploadDeviceGPS(@RequestBody Map<String, Object> params){
        return iInspectService.uploadDeviceGPS(params);
    }

    @ApiOperation("获取在线人员信息")
    @GetMapping("/getDeviceInfo")
    public RespBean getDeviceInfo(HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        return iInspectService.getDeviceInfo(request);
    }

    @ApiOperation("获取某年某月的地图概况信息")
    @GetMapping("/getMapOverviewInfo")
    public RespBean getMapOverviewInfo(String time,String unit,HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        return iInspectService.getMapOverviewInfo(time,unit,request);
    }

    @ApiOperation("openfeign:任务是否可以删除")
    @GetMapping("/isDelete")
    public Boolean isDelete(Integer routeId,Integer planId){
        return iInspectService.isDelete(routeId,planId);
    }
}

