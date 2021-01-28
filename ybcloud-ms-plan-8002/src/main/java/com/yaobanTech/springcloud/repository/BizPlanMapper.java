package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BizPlanMapper {

    @Update({"<script>",
            "update biz_plan",
            "<set>",
            "<if test='planName != null'>",
            "plan_name = #{planName} ,",
            "</if>",
            "<if test='planType != null'>",
            "plan_type = #{planType} ,",
            "</if>",
            "<if test='startTime != null'>",
            "start_time = #{startTime} ,",
            "</if>",
            "<if test='endTime != null'>",
            "end_time = #{endTime},",
            "</if>",
            "<if test='planCreatedBy != null'>",
            "plan_created_by = #{planCreatedBy} ,",
            "</if>",
            "<if test='planCreatedTime != null'>",
            "plan_created_time = #{planCreatedTime} ,",
            "</if>",
            "<if test='planStatus != null'>",
            "plan_status = #{planStatus} ,",
            "</if>",
            "<if test='actProcess != null'>",
            "act_process = #{actProcess} ,",
            "</if>",
            "<if test='planProcess != null'>",
            "plan_process = #{planProcess} ,",
            "</if>",
            "<if test='planPorid != null'>",
            "plan_porid = #{planPorid} ,",
            "</if>",
            "<if test='taskDesc != null'>",
            "task_desc = #{taskDesc},",
            "</if>",
            "<if test='area != null'>",
            "area = #{area} ,",
            "</if>",
            "<if test='memo != null'>",
            "memo = #{memo} ," ,
            "</if>",
            "<if test='enabled != null'>",
            "enabled = #{enabled} ," ,
            "</if>",
            "<if test='routeId != null'>",
            "route_id = #{routeId} ," ,
            "</if>",
            "<if test='mainKey != null'>",
            "main_key = #{mainKey} ," ,
            "</if>",
            "<if test='troubleCode != null'>",
            "trouble_code = #{troubleCode} ," ,
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"})
    void update(BizPlan bizPlan);
}