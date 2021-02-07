package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface BizSignPointMapper {

    @Update({"<script>",
            "update biz_sign_point",
            "<set>",
            "<if test='signPointCode != null'>",
            "sign_point_code = #{signPointCode} ,",
            "</if>",
            "<if test='currentChoosedAddress != null'>",
            "current_choosed_address = #{currentChoosedAddress} ,",
            "</if>",
            "<if test='troubleCode != null'>",
            "trouble_code = #{troubleCode} ,",
            "</if>",
            "<if test='troubleReason != null'>",
            "trouble_reason = #{troubleReason} ,",
            "</if>",
            "<if test='hiddenDangerType != null'>",
            "hidden_danger_type = #{hiddenDangerType} ,",
            "</if>",
            "<if test='handleSuggestion != null'>",
            "handle_suggestion = #{handleSuggestion} ,",
            "</if>",
            "<if test='location != null'>",
            "location = #{location} ,",
            "</if>",
            "<if test='pipeDiameter != null'>",
            "pipe_diameter = #{pipeDiameter} ,",
            "</if>",
            "<if test='memo != null'>",
            "memo = #{memo},",
            "</if>",
            "<if test='siteConditions != null'>",
            "site_conditions = #{siteConditions} ,",
            "</if>",
            "<if test='siteConditionsDesc != null'>",
            "site_conditions_desc = #{siteConditionsDesc} ," ,
            "</if>",
            "<if test='hiddenDangerAddress != null'>",
            "hidden_danger_address = #{hiddenDangerAddress} ," ,
            "</if>",
            "<if test='hiddenDangerReason != null'>",
            "hidden_danger_reason = #{hiddenDangerReason} ," ,
            "</if>",
            "<if test='dischargeAddress != null'>",
            "discharge_address = #{dischargeAddress} ," ,
            "</if>",
            "<if test='dischargeTimeLast != null'>",
            "discharge_time_last = #{dischargeTimeLast} ," ,
            "</if>",
            "<if test='dischargeTime != null'>",
            "discharge_time = #{dischargeTime} ," ,
            "</if>",
            "<if test='estimatedDischarge != null'>",
            "estimated_discharge = #{estimatedDischarge} ," ,
            "</if>",
            "<if test='routeType != null'>",
            "route_type = #{routeType} ," ,
            "</if>",
            "<if test='pointInspectionType != null'>",
            "point_inspection_type = #{pointInspectionType} ," ,
            "</if>",
            "<if test='signPointStatus != null'>",
            "sign_point_status = #{signPointStatus} ," ,
            "</if>",
            "<if test='modifyTime != null'>",
            "modify_time = #{modifyTime} ," ,
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"})
    void update(BizSignPoint bizSignPoint);

    @Select(value="SELECT b.route_id as routeIds,b.id as planIds " +
            "FROM `ybcloud-ms-plan-8002`.`biz_plan` b " +
            "JOIN `ybcloud-ms-route-8001`.`biz_route` a ON a.id = b.route_id " +
            "WHERE 1=1 " +
            "AND IF(#{waterManagementOffice} is not null,water_management_office = #{waterManagementOffice},1=1) " +
            "AND IF(#{routeName} is not null,route_name = #{routeName},1=1) " +
            "AND IF(#{pointInspectionType} is not null,point_inspection_type = #{pointInspectionType},1=1) " +
            "AND IF(#{planName} is not null,plan_name = #{planName},1=1) " +
            "AND IF(#{planPorid} is not null,plan_porid = #{planPorid},1=1) " +
            "AND IF(#{planType} is not null,plan_type = #{planType},1=1) ")

    List<HashMap<String,Object>> findRouteIds(String waterManagementOffice, String routeName, String pointInspectionType, String planName , String planPorid, String planType);
}