package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface BizRouteRepository extends JpaRepository<BizRoute,Integer>, JpaSpecificationExecutor<BizRoute> {

    @Modifying
    @Query("update BizRoute t set t.enabled = 0 where t.id = ?1")
    Integer deleteRoute(Integer id);

    @Query(value = "from BizRoute t where t.routeCreator = ?1 order by t.enabled desc")
    List<BizRoute> findList(String user);

    @Query(value = "from BizRoute t where t.routeCreator = ?1 and t.enabled = 1")
    List<BizRoute> findExitList(String user);

    @Query(value = "select * from biz_route where id = ?1",nativeQuery = true)
    BizRoute findDetail(Integer id);

    @Query(value = "select new map(b.id,b.routeName) from BizRoute b where b.enabled = 1 and b.waterManagementOffice = ?1")
    List<HashMap<String,Object>> findSelection(String code);

    @Modifying
    @Query("update BizRoute t set t.enabled = t.enabled + 1 where t.id = ?1")
    Integer testFeign(Integer id);

}
