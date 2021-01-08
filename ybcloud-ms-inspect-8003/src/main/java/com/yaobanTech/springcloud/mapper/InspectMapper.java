package com.yaobanTech.springcloud.mapper;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface InspectMapper extends BaseMapper<Inspect> {

    //获取班组员的未处理列表


}
