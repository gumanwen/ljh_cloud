package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface BizPlanRepository extends JpaRepository<BizPlan,Integer>, JpaSpecificationExecutor<BizPlan> {

    @Modifying
    @Query("update BizPlan t set t.enabled = 0 where t.id = ?1")
    void deletePlan(Integer id);

    @Modifying
    @Query("update BizPlan t set t.planStatus = ?2 where t.id = ?1")
    void examinePlan(Integer id,String status);

    @Query("select t.routeId from  BizPlan t where t.id = ?1")
    Integer findRouteId(Integer planId);

    @Query("from BizPlan t where id = ?1  and t.enabled = 1")
    BizPlan findDetail(Integer id);

    @Query("select t.planName from BizPlan t where t.waterUseOffice = ?1  and t.enabled = 1")
    List<String> findPlanName(String waterUseOffice);

    @Query("from BizPlan t where t.routeId = ?1  and t.enabled = 1")
    List<BizPlan> findByRouteId(Integer routeId);

    @Query(value = "select new map(t.id,t.planName) from BizPlan t where t.enabled = 1 and t.routeId = ?1")
    List<HashMap<String,Object>> findSelection(Integer routeId);

    @Query(value="SELECT a.water_management_office,a.route_name,b.* " +
            "FROM `ybcloud-ms-plan-8002`.`biz_plan` b " +
            "JOIN `ybcloud-ms-route-8001`.`biz_route` a ON a.id = b.route_id " +
            "WHERE a.enabled = 1 and b.enabled = 1 " +
            "AND (?1 is null or ?1='' or a.route_name = ?1 )" +
            "AND (?2 is null or ?2='' or a.water_management_office = ?2 ) " +
            "AND (?3 is null or ?3='' or b.plan_porid = ?3 ) " +
            "AND (?4 is null or ?4='' or b.plan_type = ?4 ) " +
            "AND (?5 is null or ?5='' or b.plan_created_time >= ?5 ) " +
            "AND (?6 is null or ?6='' or b.plan_created_time <= ?6 ) " +
            "AND (?7 is null or ?7='' or b.start_time >= ?7 ) " +
            "AND (?8 is null or ?8='' or b.start_time <= ?8 ) " +
            "AND (?9 is null or ?9='' or b.end_time >= ?9 ) " +
            "AND (?10 is null or ?10='' or b.end_time <= ?10 ) " +
            "ORDER BY b.start_time DESC",nativeQuery = true)
    List<BizPlan> findCondition(String routeName, String waterManagementOffice, String planPorid, String planType,
                                           String startTimeOfPCT, String endTimeOfPCT,
                                           String startTimeOfPST, String endTimeOfPST,
                                           String startTimeOfPET, String endTimeOfPET);
}
