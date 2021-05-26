package com.yaobanTech.springcloud.mapper;

import com.yaobanTech.springcloud.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lijh
 * @since 2020-12-27
 */
@Component
public interface FilesMapper extends BaseMapper<Files> {

    @Select(value = "insert into biz_files (`name`,pid,url,upload_date,remark,`type`,mime_type,isvalid) select `name`,#{pid} as pid,url,upload_date,remark,'qddfj' as type,mime_type,isvalid from  biz_files where pid =#{code} and type = 'yhdfj'")
    void copyFiles(@Param("pid") String pid, @Param("code")String code);
}
