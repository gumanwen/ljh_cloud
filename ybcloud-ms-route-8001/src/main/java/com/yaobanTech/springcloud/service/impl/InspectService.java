package com.yaobanTech.springcloud.service.impl;

import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(value ="inspectService")
public interface InspectService {

    @GetMapping("/api/inspect/getTaskListByTime")
    RespBean getTaskIds(@RequestParam("taskStart1")Date taskStart1,@RequestParam("taskEnd1")Date taskEnd1,
                        @RequestParam("taskStart2")Date taskStart2,@RequestParam("taskEnd2")Date taskEnd2,
                        @RequestParam("checkMan")String checkMan);

    @GetMapping("/api/inspect/isModifiable")
    RespBean comfirmModify(@RequestParam("routeId") Integer routeId);

    @GetMapping("/api/inspect/deleteRoute")
    Boolean deleteRoute(@RequestParam("routeId") Integer routeId);

}