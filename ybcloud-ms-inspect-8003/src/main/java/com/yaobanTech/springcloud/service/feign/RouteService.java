package com.yaobanTech.springcloud.service.feign;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(value ="routeService" )
public interface RouteService {
    /*签到点详情*/
    @GetMapping("/api/route/signPoint/findSignPoint")
    RespBean findsignPoint(@RequestParam("id")Integer id);
    /*签到点列表*/
    @GetMapping("/api/route/signPoint/findList")
    RespBean findpoints(@RequestParam("routeId") Integer routeId);
    /*新签到点全部列表*/
    @GetMapping("/api/route/signPoint/findListByTaskId")
    RespBean findListByTaskId(@RequestParam("routeId") Integer routeId , @RequestParam("inspectTaskId") String inspectTaskId);
    /*修改签到点*/
    @PostMapping("/api/route/signPoint/modify")
    RespBean updatePoint(@RequestBody Map<String, Object> params);
    /*路线详情*/
    @GetMapping("/api/route/way/findDetail")
    RespBean findDetail(@RequestParam("id")Integer id);
    /*下拉框列表*/
    @GetMapping("/api/route/way/findSelection")
    RespBean findSelection(@RequestParam("code") String code);
    /*已签到点列表*/
    @GetMapping("/api/route/signPoint/findSignedList")
    RespBean findSignedList(@RequestParam("routeId") Integer routeId,@RequestParam("taskId") String taskId);
    /*枚举值*/
    @GetMapping("/api/route/way/findEnum")
    RespBean findEnum(@RequestParam("code")String code);
    /*新增任务签到点*/
    @GetMapping("/api/route/signPoint/taskPoint")
    RespBean taskPoint(@RequestParam("taskIds") List<String> taskIds, @RequestParam("routeId") Integer routeId);
}