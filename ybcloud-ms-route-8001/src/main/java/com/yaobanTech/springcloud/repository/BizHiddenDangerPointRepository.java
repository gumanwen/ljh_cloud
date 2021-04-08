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

    @Query("update BizHiddenDangerPointEntity t set t.memo = ?2 where t.hiddenDangerPointCode = ?1")
    void synchronization(String code,String handle);

    @Modifying
    @Query("update BizHiddenDangerPointEntity t set t.hiddenDangerStatus = 54,t.opration = ?2 where t.hiddenDangerPointCode = ?1")
    void updateHiddenDangerPoint(String hiddenDangerPointCode,String opration);

    @Modifying
    @Query("update BizHiddenDangerPointEntity t set t.hiddenDangerStatus = 55 where t.hiddenDangerPointCode = ?1")
    void updateHiddenDangerPointStatus(String hiddenDangerPointCode);

    @Query(value = " from BizHiddenDangerPointEntity t where t.enabled = 1 and t.id = ?1")
    BizHiddenDangerPointEntity findHiddenDangerPoint(Integer id);

    @Query(value = " from BizHiddenDangerPointEntity t where t.enabled = 1 and t.hiddenDangerPointCode = ?1")
    BizHiddenDangerPointEntity findHiddenDangerPoint(String code);

    @Query(value = " from BizHiddenDangerPointEntity t where t.enabled = 1 and t.commitBy = ?1")
    List<BizHiddenDangerPointEntity> findList(String user);

    @Query(value = "select count(*) from biz_hidden_danger_point a where a.hidden_danger_point_status = '54' ",nativeQuery = true)
    Integer countFollowed();

    @Query(value = "select count(*) from biz_hidden_danger_point a",nativeQuery = true)
    Integer countAll();
}
