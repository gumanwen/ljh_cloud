package com.yaobanTech.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaobanTech.springcloud.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
@Mapper
@Component
public interface TestMapper extends BaseMapper<Test> {

    void updateAll(@Param("list") List<Test> result,int n);

    List<Test> selectAllList(Integer gid);
}
