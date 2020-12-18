package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BizRouteRepository extends JpaRepository<BizRoute,Integer> {

}
