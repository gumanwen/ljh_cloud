package com.yaobanTech.springcloud.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yaobanTech.springcloud.entity.Inspect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    //根据日查询出巡查任务完成率
    List<HashMap<String, Object>> selectTasksRateByDays(String waterManagementOffice, String beginTime, String deadTime);
    //根据日查询出巡查任务到位率
    List<HashMap<String, Object>> selectTaskDwlByDays(String waterManagementOffice, String beginTime, String deadTime);
    //根据日查询出巡查日志记录
    List<HashMap<String, Object>> selectTaskslogByDays(String waterManagementOffice, String beginTime, String deadTime);
    //自定义查询中文名
    @Select("SELECT a.*,b.name from [ybcloud-ms-inspect-8003].dbo.[BIZ_INSPECT] m left join  [ybcloud-cs-auth-6002].dbo.[SEC_USER] n on m.inspect_person = n.username  ${ew.customSqlSegment}")
    <E extends IPage<Inspect>> E selectdiyPage(E page, @Param(Constants.WRAPPER) Wrapper<Inspect> queryWrapper);
    //获取所有人的完成率和到位率
    List<HashMap<String, Object>> selectwclAnddwlByyear(String time);
    //获取所有人的巡查轨迹
    List<HashMap<String, Object>> selectInspectMileByyear(String username,String time);
    //获取所有人的完成率和到位率
    List<HashMap<String, Object>> selectwclAnddwlBymonth(String time);
    //获取所有人的巡查轨迹
    List<HashMap<String, Object>> selectInspectMileBymonth(String username,String time);

    List<HashMap<String, Object>> selectOfficeInfoByYear(String time);

    List<HashMap<String, Object>> selectOfficeInfoByMonth(String time);
}
