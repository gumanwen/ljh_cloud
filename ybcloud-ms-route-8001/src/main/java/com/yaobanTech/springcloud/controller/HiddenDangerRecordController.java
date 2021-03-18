package com.yaobanTech.springcloud.controller;

import com.yaobanTech.springcloud.domain.BizHiddenDangerRecord;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.service.impl.HiddenDangerRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description = "隐患记录接口")
@RequestMapping("/api/route/hiddenDangerRecord")
public class HiddenDangerRecordController {

    @Autowired
    @Lazy
    private HiddenDangerRecordService hiddenDangerRecordService;

    @ApiOperation("保存隐患记录")
    @PostMapping("add")
    public RespBean saveHiddenDangerRecord(@RequestBody BizHiddenDangerRecord hiddenDangerRecord) {
        return hiddenDangerRecordService.saveHiddenDangerRecord(hiddenDangerRecord);
    }

    @ApiOperation("查询隐患记录列表")
    @GetMapping("findList")
    public RespBean findList(@RequestParam String hiddenDangerCode,
                             @RequestParam long page,
                             @RequestParam long total) {
        return hiddenDangerRecordService.findList(hiddenDangerCode,page,total);
    }
}
