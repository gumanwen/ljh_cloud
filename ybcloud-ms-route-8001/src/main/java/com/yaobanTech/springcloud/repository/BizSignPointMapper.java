package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Select(value="SELECT a.*,b.*,b.id as plan_id " +
            "FROM `ybcloud-ms-plan-8002`.`biz_plan` b " +
            "JOIN `ybcloud-ms-route-8001`.`biz_route` a ON a.id = b.route_id " +
            "WHERE 1=1 " +
            "AND IF(#{waterManagementOffice} is not null,a.water_management_office = #{waterManagementOffice},1=1) " +
            "AND IF(#{routeId} is not null,a.id = #{routeId},1=1) " +
            "AND IF(#{pointInspectionType} is not null,a.point_inspection_type = #{pointInspectionType},1=1) " +
            "AND IF(#{planId} is not null,b.id = #{planId},1=1) " +
            "AND IF(#{planPorid} is not null,b.plan_porid = #{planPorid},1=1) " +
            "AND IF(#{routeType} is not null,a.route_type = #{routeType},1=1) " +
            "AND IF(#{planType} is not null,b.plan_type = #{planType},1=1) ")

    List<HashMap<String,Object>> findRouteIds(@Param("waterManagementOffice") String waterManagementOffice,@Param("routeId") Integer routeId, @Param("pointInspectionType")String pointInspectionType,@Param("planId") Integer planId , @Param("planPorid")String planPorid,@Param("planType") String planType,@Param("routeType") String routeType);

    @Select(value="SELECT a.* " +
            "FROM `ybcloud-ms-route-8001`.`biz_route` a " +
            "WHERE 1=1 " +
            "AND IF(#{waterManagementOffice} is not null,a.water_management_office = #{waterManagementOffice},1=1) " +
            "AND IF(#{pointInspectionType} is not null,a.point_inspection_type = #{pointInspectionType},1=1) " +
            "AND IF(#{planInspectionMileageStart} is not null,a.plan_inspection_mileage >= #{planInspectionMileageStart},1=1) " +
            "AND IF(#{planInspectionMileageEnd} is not null,a.plan_inspection_mileage <= #{planInspectionMileageEnd},1=1) " +
            "AND IF(#{createdTimeStart} is not null,a.created_time >= #{createdTimeStart},1=1) " +
            "AND IF(#{createdTimeEnd} is not null,a.created_time <= #{createdTimeEnd},1=1) " +
            "AND IF(#{routeName} is not null,a.route_name = #{routeName},1=1) " +
            "AND IF(#{routeType} is not null,a.route_type = #{routeType},1=1) " +
            "AND (IF(#{mainKey} is not null,a.area = #{mainKey},1=1) " +
            "OR IF(#{mainKey} is not null,a.route_creator = #{mainKey},1=1) " +
            "OR IF(#{mainKey} is not null,a.route_name = #{mainKey},1=1)) ")
    List<BizRoute> findRouteCondition(RouteCondition routeCondition);


    @Select(value="SELECT a.* " +
            "FROM `ybcloud-ms-route-8001`.`biz_leak_point` a " +
            "WHERE a.enabled = 1 " +
            "AND IF(#{assetType} is not null,  a.asset_type = #{assetType},1=1)" +
            "AND IF(#{equipmentSize} is not null, a.equipment_size = #{equipmentSize},1=1 ) " +
            "AND IF(#{leakPointStatus} is not null, a.leak_point_status = #{leakPointStatus},1=1 ) " +
            "AND IF(#{waterUseOffice} is not null, a.water_use_office = #{waterUseOffice},1=1 ) " +
            "ORDER BY a.commit_Date DESC")
    List<BizLeakPointEntity> leakPointQuery(LeakPointQuery leakPointQuery);

    @Select(value="SELECT a.* " +
            "FROM `ybcloud-ms-route-8001`.`biz_hidden_danger_point` a " +
            "WHERE a.enabled = 1 " +
            "AND IF(#{waterUseOffice} is not null, a.water_use_office = #{waterUseOffice},1=1 ) " +
            "AND IF(#{hiddenDangerPointStatus} is not null, a.hidden_danger_point_status = #{hiddenDangerPointStatus},1=1 ) " +
            "AND IF(#{constructionS1} is not null, a.construction_start_date >= #{constructionS1},1=1 ) " +
            "AND IF(#{constructionS2} is not null, a.construction_start_date < #{constructionS2},1=1 ) " +
            "AND IF(#{constructionE1} is not null, a.construction_start_date >= #{constructionE1},1=1 ) " +
            "AND IF(#{constructionE2} is not null, a.construction_start_date < #{constructionE2},1=1 ) " +
            "AND IF(#{commitDate} is not null, a.commit_date >= #{commitDate},1=1 ) " +
            "AND IF(#{endDate} is not null, a.end_date <= #{endDate},1=1 ) " +
            "AND IF(#{hiddenDangerPointStatus} is not null, a.hidden_danger_point_status = #{hiddenDangerPointStatus},1=1 ) " +
            "AND IF(#{riskLevel} is not null, a.risk_level = #{riskLevel},1=1 ) " +
            "AND IF(#{projectType} is not null, a.project_type = #{projectType},1=1 ) " +
            "AND IF(#{constructionType} is not null, a.construction_type = #{constructionType},1=1 ) " +
            "AND IF(#{networkNotification} is not null, a.network_notification = #{networkNotification},1=1 ) " +
            "AND ( IF(#{mainKey} is not null, a.project_name = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, a.construction_position = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, a.build_company = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, a.build_company_agent = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, a.construction_company = #{mainKey},1=1 ) " +
            "OR IF(#{mainKey} is not null, a.reason like #{mainKey},1=1 )) " +
            "ORDER BY a.commit_Date DESC")
    List<BizHiddenDangerPointEntity> hiddenDangerPointQuery(HiddenDangerPointQuery hiddenDangerPointQuery);

    @Select(value="SELECT  a.*,b.plan_name,c.* " +
            "FROM `ybcloud-ms-route-8001`.`biz_route` a " +
            "JOIN `ybcloud-ms-plan-8002`.`biz_plan` b ON a.id = b.route_id " +
            "JOIN `ybcloud-ms-route-8001`.`biz_signed_point` c ON a.id = c.route_id " +
            "WHERE a.enabled = 1 and b.enabled = 1 and c.enabled = 1 " +
            "AND IF(#{waterUserOffice} is not null,  a.water_management_office = #{waterUserOffice},1=1) " +
            "AND IF(#{routeName} is not null, a.route_name = #{routeName},1=1 ) " +
            "AND IF(#{routeType} is not null, a.route_type = #{routeType},1=1 ) " +
            "AND IF(#{pointInspectionType} is not null, a.point_inspection_type = #{pointInspectionType},1=1 ) " +
            "AND IF(#{planName} is not null, b.plan_name = #{planName},1=1 ) " +
            "AND IF(#{signDateStart} is not null, c.signed_time >= #{signDateStart},1=1 ) " +
            "AND IF(#{signDateEnd} is not null, c.signed_time <= #{signDateEnd},1=1 ) " +
            "AND IF(#{signPointType} is not null, c.sign_point_type = #{signPointType},1=1 ) " +
            "AND IF(#{pipeDiameter} is not null, c.pipe_diameter = #{pipeDiameter},1=1 ) " +
            "AND IF(#{signStatus} is not null, c.sign_point_status = #{signStatus},1=1 ) " +
            "AND IF(#{hiddenCode} is not null, c.trouble_code = #{hiddenCode},1=1 ) " +
            "ORDER BY c.modify_time DESC")
    List<HashMap<String,Object>> findCondition(SignPointQuery signPointQuery);

    @Select(value="SELECT a.*,b.plan_name,c.*,c.id as sign_point_id,c.signed_time " +
            "FROM `ybcloud-ms-route-8001`.`biz_route` a " +
            "right JOIN `ybcloud-ms-route-8001`.`biz_signed_point` c ON a.id = c.route_id " +
            "JOIN `ybcloud-ms-plan-8002`.`biz_plan` b on b.id = c.plan_id "+
            "WHERE a.enabled = 1 and b.enabled = 1 and c.enabled = 1 " +
            "AND IF(#{waterUserOffice} is not null,a.water_management_office = #{waterUserOffice},1=1) " +
            "AND IF(#{routeName} is not null, a.route_name = #{routeName},1=1 ) " +
            "AND IF(#{routeType} is not null, a.route_type = #{routeType},1=1 ) " +
            "AND IF(#{pointInspectionType} is not null, a.point_inspection_type = #{pointInspectionType},1=1 ) " +
            "AND IF(#{planName} is not null, b.plan_name = #{planName},1=1 ) " +
            "AND IF(#{signDateStart} is not null, c.signed_time >= #{signDateStart},1=1 ) " +
            "AND IF(#{signDateEnd} is not null, c.signed_time <= #{signDateEnd},1=1 ) " +
            "AND IF(#{signPointType} is not null, c.sign_point_type = #{signPointType},1=1 ) " +
            "AND IF(#{pipeDiameter} is not null, c.pipe_diameter = #{pipeDiameter},1=1 ) " +
            "AND IF(#{signStatus} is not null, c.sign_point_status = #{signStatus},1=1 ) " +
            "AND IF(#{hiddenCode} is not null, c.trouble_code = #{hiddenCode},1=1 ) " +
            "AND IF(#{mainKey} is not null, c.site_conditions_desc = #{mainKey},1=1 ) " +
            "AND FIND_IN_SET(c.task_id, #{taskidList}) " +
            "ORDER BY c.modify_time DESC")
    List<HashMap<String,Object>> findConditionElse(SignPointQuery signPointQuery);


    @Select(value = "SELECT * FROM `biz_hidden_danger_point` WHERE 1 = 1 " +
            "AND IF( #{waterUseOffice} not like '', water_use_office = #{waterUseOffice}, 1 = 1 ) " +
//            "and end_date is null " +
//            "and IF ( #{end} IS NOT NULL, commit_date <= #{end}, 1=1 ) " +
//            "or end_date is not null " +
//            "AND IF ( #{start} IS NOT NULL, commit_date >#{start}, commit_date > ( SELECT DATE_SUB( CURDATE( ), INTERVAL 12 DAY ) ) ) " +
//            "and IF ( #{end} IS NOT NULL, commit_date <= #{end}, commit_date <= ( SELECT DATE_SUB( now( ), INTERVAL 1 SECOND ) ) ) ")
            "AND IF ( #{start} not like '', commit_date >=#{start}, 1 = 1 ) " +
            "and IF ( #{end} not like '', DATE_FORMAT(commit_date,'%Y-%m-%d') <= #{end}, 1 = 1 ) ")
    List<BizHiddenDangerPointEntity> countDangerPointList(@Param("waterUseOffice") String waterUseOffice ,  @Param("start") String start ,  @Param("end")String end);

    @Select(value="SELECT a.*,c.*,(@i :=@i+1) AS rowid " +
            "FROM `ybcloud-ms-route-8001`.`biz_route` a " +
            "right JOIN `ybcloud-ms-route-8001`.`biz_signed_point` c ON a.id = c.route_id, " +
            "(SELECT @i :=0) AS it WHERE a.enabled = 1 and c.enabled = 1 " +
            "AND IF(#{waterUseOffice}  not like '',a.water_management_office = #{waterUseOffice},1=1) " +
            "AND IF(#{start}  not like '',c.signed_time >= #{start},1=1) " +
            "AND IF(#{end}  not like '',c.signed_time < #{end},1=1) " +
            "ORDER BY a.created_time DESC")
    List<HashMap<String,Object>> findConditionByEndPoint(@Param("waterUseOffice") String waterUseOffice , @Param("start")String start , @Param("end")String end);

    @Select(value = "SELECT a.sum + b.sum FROM ( " +
            "SELECT count( * ) AS sum FROM `biz_hidden_danger_point` WHERE 1 = 1 " +
            "AND IF ( #{waterUseOffice} IS NOT NULL, `biz_hidden_danger_point`.water_use_office = #{waterUseOffice}, 1 = 1 ) " +
            "AND IF ( #{start} IS NOT NULL, `biz_hidden_danger_point`.commit_date > #{start}, `biz_hidden_danger_point`.commit_date > ( SELECT DATE_SUB( CURDATE( ), INTERVAL 12 DAY ))) " +
            "AND IF ( #{start} IS NOT NULL, `biz_hidden_danger_point`.commit_date < #{end}, `biz_hidden_danger_point`.commit_date < ( SELECT date_sub(now(),interval 1 second))) " +
            ") a, " +
            "( SELECT count( * ) AS sum FROM `biz_leak_point` WHERE 1 = 1 " +
            "AND IF ( #{waterUseOffice} IS NOT NULL, `biz_leak_point`.water_use_office = #{waterUseOffice}, 1 = 1 ) " +
            "AND IF ( #{start} IS NOT NULL, `biz_leak_point`.commit_date > #{start}, `biz_leak_point`.commit_date > ( SELECT DATE_SUB( CURDATE( ), INTERVAL 12 DAY ))) " +
            "AND IF ( #{end} IS NOT NULL, `biz_leak_point`.commit_date < #{end}, `biz_leak_point`.commit_date < ( SELECT date_sub(now(),interval 1 second))) " +
            ") b ")
    Integer countSum(@Param("waterUseOffice") String waterUseOffice , @Param("start")String start , @Param("end")String end);

    @Select(value = "SELECT\n" +
            "\tcount( * ) * 1.0 AS k \n" +
            "FROM\n" +
            "\t`biz_hidden_danger_point` \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "AND\n" +
            "IF\n" +
            "\t( #{waterUseOffice} IS NOT NULL, water_use_office = #{waterUseOffice}, 1 = 1 ) \n" +
            "AND\n" +
            "IF\n" +
            "\t( #{start} IS NOT NULL, commit_date >#{start}, commit_date > ( SELECT DATE_SUB( CURDATE( ), INTERVAL 12 DAY ) ) ) \n" +
            "AND\n" +
            "IF\n" +
            "\t( #{end} IS NOT NULL, commit_date <#{end}, commit_date < ( SELECT DATE_SUB( now( ), INTERVAL 1 SECOND ) ) )")
    Double countDangerPoint(@Param("waterUseOffice") String waterUseOffice , @Param("start")String start , @Param("end")String end);

    @Select(value = "SELECT\n" +
            "\tcount( * ) * 1.0 AS k \n" +
            "FROM\n" +
            "\t`biz_leak_point` \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "AND\n" +
            "IF\n" +
            "\t( #{waterUseOffice} IS NOT NULL, water_use_office = #{waterUseOffice}, 1 = 1 ) \n" +
            "AND\n" +
            "IF\n" +
            "\t( #{start} IS NOT NULL, commit_date >#{start}, commit_date > ( SELECT DATE_SUB( CURDATE( ), INTERVAL 12 DAY ) ) ) \n" +
            "AND\n" +
            "IF\n" +
            "\t( #{end} IS NOT NULL, commit_date <#{end}, commit_date < ( SELECT DATE_SUB( now( ), INTERVAL 1 SECOND ) ) )")
    Double countLeakPoint(@Param("waterUseOffice") String waterUseOffice , @Param("start")String start , @Param("end")String end);

}
