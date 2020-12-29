package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizRouteRepository extends JpaRepository<BizRoute,Integer>, JpaSpecificationExecutor<BizRoute> {

    @Modifying
    @Query("update BizRoute t set t.enabled = 0 where t.id = ?1")
    Integer deleteRoute(Integer id);

    @Query(value = "select * from biz_route where enabled = 1 ",nativeQuery = true)
    List<BizRoute> findList();

    @Query(value = "select * from biz_route where enabled = 1 and id = ?1",nativeQuery = true)
    BizRoute findDetail(Integer id);

    @Query(value = "select id,route_name from biz_route where enabled = 1",nativeQuery = true)
    List<Selection> findSelection();

}
