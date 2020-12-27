package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizPlanRepository extends JpaRepository<BizPlan,Integer>, JpaSpecificationExecutor<BizPlan> {

    @Query("update BizPlan t set t.enabled = '0' where t.id = ?1")
    void deletePlan(Integer id);

    @Query("select t.routeId from  BizPlan t where t.id = ?1")
    Integer findRouteId(Integer planId);

}
