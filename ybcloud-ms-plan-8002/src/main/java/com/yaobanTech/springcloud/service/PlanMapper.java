package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.BizPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

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
}