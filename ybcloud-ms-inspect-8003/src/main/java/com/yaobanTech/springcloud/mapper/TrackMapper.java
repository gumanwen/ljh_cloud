package com.yaobanTech.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaobanTech.springcloud.entity.Track;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.Date;
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
public interface TrackMapper extends BaseMapper<Track> {
    //获取班组员的未处理列表
    List<Track> selectTrackList(String person, Date begin, Date end);
}
