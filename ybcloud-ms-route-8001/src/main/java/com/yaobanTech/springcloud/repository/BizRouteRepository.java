package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BizRouteRepository extends JpaRepository<BizRoute,Integer>, JpaSpecificationExecutor<BizRoute> {

    @Query("update BizRoute t set t.enabled = '0' where t.id = ?1")
    BizRoute deleteRoute(Integer id);

}
