package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BizSignPointRepository extends JpaRepository<BizSignPoint,Integer>, JpaSpecificationExecutor<BizSignPoint> {

    @Query("update BizSignPoint t set t.enabled = '0' where t.id = ?1")
    BizSignPoint deleteSignPoint(Integer id);

}
