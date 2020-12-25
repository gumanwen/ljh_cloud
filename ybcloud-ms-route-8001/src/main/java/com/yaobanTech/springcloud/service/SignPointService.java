package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient("signPointService")
public interface SignPointService {

    @PostMapping("/api/route/signPoint/")
    public RespBean saveSignPoint(BizSignPoint bizSignPoint);

    @PutMapping("/api/route/signPoint/{id}")
    public RespBean updateSignPoint(@PathVariable("id") Integer id, @RequestBody BizSignPoint signPoint);

    @DeleteMapping("/api/route/signPoint/{id}")
    public RespBean deleteSignPoint(Integer id);

    @GetMapping("/api/route/signPoint/")
    public RespBean getSignPoint(Integer id);

}
