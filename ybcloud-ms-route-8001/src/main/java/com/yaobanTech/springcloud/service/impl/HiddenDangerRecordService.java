package com.yaobanTech.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaobanTech.springcloud.domain.BizHiddenDangerRecord;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizHiddenDangerRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class HiddenDangerRecordService {
    @Autowired
    @Lazy
    private BizHiddenDangerRecordMapper bizHiddenDangerRecordMapper;

    public RespBean saveHiddenDangerRecord(BizHiddenDangerRecord bizHiddenDangerRecord){
        Integer count = null;
        if(bizHiddenDangerRecord != null){
            try {
                count = bizHiddenDangerRecordMapper.insert(bizHiddenDangerRecord);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return RespBean.error("参数不能为空!");
        }
        return RespBean.ok("保存成功,执行结果条数",count);
    }

    public RespBean findList(String hiddenDangerCode,Long page,Long total){
        IPage iPage = null;
        if(hiddenDangerCode != null){
            try {
                 iPage = bizHiddenDangerRecordMapper.selectPage(new Page<BizHiddenDangerRecord>(page, total), new LambdaQueryWrapper<BizHiddenDangerRecord>().
                        eq(hiddenDangerCode != null, BizHiddenDangerRecord::getHiddenDangerCode, hiddenDangerCode));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return RespBean.error("参数不能为空!");
        }
        return RespBean.ok("查询成功",iPage);
    }
}
