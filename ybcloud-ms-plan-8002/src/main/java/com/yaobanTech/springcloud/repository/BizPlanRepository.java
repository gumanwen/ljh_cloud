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
    @Query("update BizPlan t set t.enabled = 0,t.planStatus = '16' where t.id = ?1")
    void deletePlan(Integer id);

    @Modifying
    @Query("update BizPlan t set t.planStatus = ?2,t.examineDate = ?3,t.examineAdvice = ?4 where t.id = ?1")
    void examinePlan(Integer id,String status,String examineDate,String examineAdvice);

    @Query("select t.routeId from  BizPlan t where t.id = ?1")
    Integer findRouteId(Integer planId);

    @Query("from BizPlan t where id = ?1")
    BizPlan findDetail(Integer id);

    @Query("select t.planName from BizPlan t where t.waterUseOffice = ?1  and t.enabled = 1")
    List<String> findPlanName(String waterUseOffice);

    @Query("from BizPlan t where t.routeId = ?1  and t.planStatus = '14'")
    List<BizPlan> findByRouteId(Integer routeId);

    @Query(value = "select new map(t.id,t.planName) from BizPlan t where t.enabled = 1 and t.routeId = ?1")
    List<HashMap<String,Object>> findSelection(Integer routeId);

}
