package com.yaobanTech.springcloud.service.feign;


import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value ="routeService" )
public interface RouteService {

    @GetMapping("/api/route/signPoint/findSignPoint")
    RespBean findsignPoint(@RequestParam("id")Integer id);

    @GetMapping("/api/route/signPoint/findList")
    RespBean findpoints(@RequestParam("routeId") Integer routeId);

    @GetMapping("/api/route/signPoint/findListByTaskId")
    RespBean findListByTaskId(@RequestParam("routeId") Integer routeId , @RequestParam("inspectTaskId") String inspectTaskId);

    @PostMapping("/api/route/signPoint/modify")
    RespBean updatePoint(@RequestBody Map<String, Object> params);

    @GetMapping("/api/route/way/findDetail")
    RespBean findDetail(@RequestParam("id")Integer id);

    @GetMapping("/api/route/way/findSelection")
    RespBean findSelection();

    @GetMapping("/api/route/signPoint/findSignedList")
    RespBean findSignedList(@RequestParam("routeId") Integer routeId,@RequestParam("taskId") String taskId);

    @GetMapping("/api/route/way/findEnum")
    RespBean findEnum(@RequestParam("code")String code);

    @GetMapping("/api/route/signPoint/taskPoint")
    RespBean taskPoint(@RequestParam("taskIds") List<String> taskIds, @RequestParam("routeId") Integer routeId);
}