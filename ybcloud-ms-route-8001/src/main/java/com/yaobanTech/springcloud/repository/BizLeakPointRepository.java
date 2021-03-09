package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizLeakPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizLeakPointRepository extends JpaRepository<BizLeakPointEntity,Integer>, JpaSpecificationExecutor<BizLeakPointEntity> {

    @Query("update BizLeakPointEntity t set t.enabled = '0' where t.id = ?1")
    void deleteBizLeakPointEntity(Integer id);

    @Modifying
    @Query("update BizLeakPointEntity t set t.leakPointStatus = 54 where t.leakPointCode = ?1")
    void updateBizLeakPoint(String leakPointCode);

    @Modifying
    @Query("update BizLeakPointEntity t set t.leakPointStatus = 55 where t.leakPointCode = ?1")
    void updateLeakPointStatus(String leakPointCode);

    @Query(value = " from BizLeakPointEntity t where t.enabled = 1 and t.id = ?1")
    BizLeakPointEntity findBizLeakPointEntity(Integer id);

    @Query(value = " from BizLeakPointEntity t where t.enabled = 1 and t.commitBy = ?1")
    List<BizLeakPointEntity> findOfList(String user);

}
