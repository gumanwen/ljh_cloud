package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizHiddenDangerPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizHiddenDangerPointRepository extends JpaRepository<BizHiddenDangerPointEntity,Integer>, JpaSpecificationExecutor<BizHiddenDangerPointEntity> {

    @Query("update BizHiddenDangerPointEntity t set t.enabled = '0' where t.id = ?1")
    void deleteHiddenDangerPoint(Integer id);

    @Modifying
    @Query("update BizHiddenDangerPointEntity t set t.hiddenDangerStatus = '已跟进' where t.hiddenDangerPointCode = ?1")
    void updateHiddenDangerPoint(String hiddenDangerPointCode);

    @Modifying
    @Query("update BizHiddenDangerPointEntity t set t.hiddenDangerStatus = 55 where t.hiddenDangerPointCode = ?1")
    void updateHiddenDangerPointStatus(String hiddenDangerPointCode);

    @Query(value = " from BizHiddenDangerPointEntity t where t.enabled = 1 and t.id = ?1")
    BizHiddenDangerPointEntity findHiddenDangerPoint(Integer id);

    @Query(value = " from BizHiddenDangerPointEntity t where t.enabled = 1 and t.commitBy = ?1")
    List<BizHiddenDangerPointEntity> findList(String user);

}
