package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizSuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<BizSuggestionEntity,Integer>, JpaSpecificationExecutor<BizSuggestionEntity> {

    @Query("update BizSuggestionEntity t set t.enabled = '0' where t.id = ?1")
    void deleteBizSuggestionEntity(Integer id);

    @Query(value = " from BizSuggestionEntity t where t.enabled = 1 and t.id = ?1")
    List<BizSuggestionEntity> findBizSuggestionEntity(Integer id);

    @Query(value = " from BizSuggestionEntity t where t.enabled = 1 and t.fCode = ?1")
    List<BizSuggestionEntity> findList(String fCode);

}
