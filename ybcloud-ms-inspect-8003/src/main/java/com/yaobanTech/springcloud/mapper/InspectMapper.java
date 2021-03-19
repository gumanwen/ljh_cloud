package com.yaobanTech.springcloud.mapper;

import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
public interface InspectMapper extends BaseMapper<Inspect> {
    //根据年份查询巡查任务数量
    List<HashMap<String,Object>> selectTasksByYears(String waterManagementOffice, String beginTime, String deadTime);
    //根据月份查询巡查任务数量
    List<HashMap<String,Object>> selectTasksByMonths(String waterManagementOffice, String beginTime, String deadTime);
    //根据日查询巡查任务数量
    List<HashMap<String,Object>> selectTasksByDays(String waterManagementOffice, String beginTime, String deadTime);
}
