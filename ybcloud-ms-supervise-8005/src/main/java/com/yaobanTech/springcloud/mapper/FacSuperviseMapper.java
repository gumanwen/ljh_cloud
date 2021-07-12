package com.yaobanTech.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaobanTech.springcloud.entity.FacSupervise;
import com.yaobanTech.springcloud.entity.SpeSupervise;
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
public interface FacSuperviseMapper extends BaseMapper<FacSupervise> {

}
