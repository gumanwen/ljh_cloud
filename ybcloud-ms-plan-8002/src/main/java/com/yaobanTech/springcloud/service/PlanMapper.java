package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.BizPlan;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface PlanMapper {

   @Select("select * from biz_plan where enabled = 1 and plan_created_by = #{user}")
   @Results(id = "mainProjectMap",
           value = {
                   @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
                   @Result(column="plan_name", property="planName", jdbcType= JdbcType.VARCHAR),
                   @Result(column="plan_type", property="planType", jdbcType= JdbcType.VARCHAR),
                   @Result(column="start_time", property="startTime", jdbcType= JdbcType.TIMESTAMP),
                   @Result(column="end_time", property="endTime", jdbcType= JdbcType.TIMESTAMP),
                   @Result(column="plan_created_by", property="planCreatedBy", jdbcType= JdbcType.VARCHAR),
                   @Result(column="plan_created_time", property="planCreatedTime", jdbcType= JdbcType.TIMESTAMP),
                   @Result(column="plan_status", property="planStatus", jdbcType= JdbcType.VARCHAR),
                   @Result(column="act_process", property="actProcess", jdbcType= JdbcType.VARCHAR),
                   @Result(column="plan_process", property="planProcess", jdbcType= JdbcType.VARCHAR),
                   @Result(column="plan_porid", property="planPorid", jdbcType= JdbcType.VARCHAR),
                   @Result(column="task_desc", property="taskDesc", jdbcType= JdbcType.VARCHAR),
                   @Result(column="area", property="area", jdbcType= JdbcType.VARCHAR),
                   @Result(column="memo", property="memo", jdbcType= JdbcType.VARCHAR),
                   @Result(column="enabled", property="enabled", jdbcType= JdbcType.INTEGER),
                   @Result(column="route_id", property="routeId", jdbcType= JdbcType.INTEGER),
                   @Result(column="main_key", property="mainKey", jdbcType= JdbcType.VARCHAR),
                   @Result(column="trouble_code", property="troubleCode", jdbcType= JdbcType.VARCHAR)
           })
   List<BizPlan>  findAll(String user);
    @Select(value="SELECT a.route_name,a.water_management_office,b.* " +
            "FROM `ybcloud-ms-plan-8002`.`biz_plan` b " +
            "JOIN `ybcloud-ms-route-8001`.`biz_route` a ON a.id = b.route_id " +
            "WHERE 1=1 " +
            "AND IF(#{routeName} is not null, a.route_name = #{routeName},1=1 ) " +
            "AND IF(#{waterManagementOffice} is not null, a.water_management_office = #{waterManagementOffice},1=1 ) " +
            "AND IF(#{planPorid} is not null, b.plan_porid = #{planPorid},1=1 ) " +
            "AND IF(#{planType} is not null, b.plan_type = #{planType},1=1 ) " +
            "AND IF(#{startTimeOfPCT} is not null, b.plan_created_time >= #{startTimeOfPCT},1=1 ) " +
            "AND IF(#{endTimeOfPCT} is not null, b.plan_created_time <= #{endTimeOfPCT},1=1 ) " +
            "AND IF(#{startTimeOfPCT} is not null, b.start_time >= #{startTimeOfPCT},1=1 ) " +
            "AND IF(#{endTimeOfPCT} is not null, b.start_time <= #{endTimeOfPCT},1=1 ) " +
            "AND IF(#{startTimeOfPET} is not null, b.end_time >= #{startTimeOfPET},1=1 ) " +
            "AND IF(#{endTimeOfPET} is not null, b.end_time <= #{endTimeOfPET},1=1 ) " +
            "AND (IF(#{mainKey} is not null, b.plan_name = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, b.plan_created_by = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, b.task_desc like #{mainKey},1=1 )) " +
            "ORDER BY b.enabled DESC ,b.plan_created_time DESC")
    List<HashMap<String,Object>> findCondition(@Param("routeName") String routeName, @Param("waterManagementOffice")String waterManagementOffice,
                                               @Param("planPorid")String planPorid, @Param("planType")String planType,
                                               @Param("startTimeOfPCT")String startTimeOfPCT, @Param("endTimeOfPCT")String endTimeOfPCT,
                                               @Param("startTimeOfPST")String startTimeOfPST, @Param("endTimeOfPST")String endTimeOfPST,
                                               @Param("startTimeOfPET")String startTimeOfPET, @Param("endTimeOfPET")String endTimeOfPET,
                                               @Param("mainKey")String mainKey);
}