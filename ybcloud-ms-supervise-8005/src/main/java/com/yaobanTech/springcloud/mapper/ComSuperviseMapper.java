package com.yaobanTech.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaobanTech.springcloud.entity.ComSupervise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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
public interface ComSuperviseMapper extends BaseMapper<ComSupervise> {

}
