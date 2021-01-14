package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.domain.BizSignPoint;
import com.yaobanTech.springcloud.domain.BizSignedPoint;
import com.yaobanTech.springcloud.domain.FieldUtils;
import com.yaobanTech.springcloud.domain.RespBean;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import com.yaobanTech.springcloud.repository.BizSignPointRepository;
import com.yaobanTech.springcloud.repository.BizSignedPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SignPointServiceImpl {
    @Autowired
    @Lazy
    private BizSignPointRepository signPointRepository;

    @Autowired
    @Lazy
    private BizSignedPointRepository signedPointRepository;

    @Autowired
    @Lazy
    private BizSignPointMapper bizSignPointMapper;

    @Autowired
    @Lazy
    private FileService fileService;

    public RespBean saveSignPoint(HashMap<String,Object> param) {
        BizSignPoint bizSignPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignPoint.class);
        if(bizSignPoint != null) {
            try {
                bizSignPoint.setEnabled(1);
                BizSignPoint signPoint = signPointRepository.save(bizSignPoint);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    public RespBean updateSignPoint(HashMap<String,Object> param) {
        BizSignedPoint bizSignedPoint = null;
        Integer id = null;
        if(!param.isEmpty() && param.get("form") != null) {
             bizSignedPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignedPoint.class);
            BizSignPoint signPoint = JSONObject.parseObject(JSONObject.toJSONString(param.get("form")), BizSignPoint.class);
            if(bizSignedPoint != null){
            try {
                bizSignedPoint.setModifyTime(new Date());
                signPointRepository.save(signPoint);
                bizSignedPoint.setSignPointStatus("已签到");
                BizSignedPoint signedPoint = signedPointRepository.save(bizSignedPoint);
                id = signedPoint.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("修改成功！",id);
        }else{
            return RespBean.error("参数为空！");
        }
    }

    public RespBean deleteSignPoint(Integer id) {
        if(id != null) {
            try {
                BizSignPoint signPoint = signPointRepository.deleteSignPoint(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    public RespBean findSignPoint(Integer id) {
        BizSignPoint byId = null;
        if(id != null) {
            try {
                 byId = signPointRepository.findSignPointById(id);

            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",byId);
    }

    public RespBean findList(Integer routeId) {
        List<BizSignPoint> list = null;
        if(routeId != null) {
            try {
                 list = signPointRepository.findSignPointListByRouteId(routeId);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findListByTaskId(Integer routeId,String taskId) {
        List<BizSignedPoint> list = null;
        if(taskId != null && routeId != null) {
            try {
                 list = signedPointRepository.findListByTaskId(routeId,taskId);
                if(list.size()>0){
                    for(int i =0; i<list.size(); i++){
                        //获取报建文件列表
                        if(FieldUtils.isObjectNotEmpty(list.get(i).getFileType())) {
                            List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) fileService.selectOneByPid(String.valueOf((Integer) list.get(i).getId()), (String) list.get(i).getFileType()).getObj();
                            list.get(i).setFileList(fileList);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("路线或任务id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean findSignedList(Integer routeId,String taskId) {
        List<BizSignedPoint> list = null;
        if(taskId != null) {
            try {
                list = signedPointRepository.findSignedList(routeId,taskId);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",list);
    }

    public RespBean taskPoint(List<String> taskIds,Integer routeId) {
        if(!taskIds.isEmpty() && routeId != null){
            List<BizSignPoint> signPointList = signPointRepository.findSignPointListByRouteId(routeId);
            for (int i = 0; i < taskIds.size(); i++) {
                for (int j = 0; j < signPointList.size(); j++) {
                    BizSignPoint bizSignPoint = signPointList.get(j);
                    HashMap<String, Object> hashMap = objectToMap(bizSignPoint);
                    hashMap.put("taskId",taskIds.get(i));
                    hashMap.put("fileType","");
                    BizSignedPoint bizSignedPoint = JSONObject.parseObject(JSONObject.toJSONString(hashMap), BizSignedPoint.class);
                    bizSignedPoint.setId(null);
                    bizSignedPoint.setSignPointStatus("未签到");
                    bizSignedPoint.setEnabled(1);
                    signedPointRepository.save(bizSignedPoint);
                }
            }
            return RespBean.ok("新建成功！");
        }
        return RespBean.error("新建异常！任务Id或路线Id不能为空！");
    }

    public static HashMap<String,Object> objectToMap(Object object){
        return JSONObject.parseObject(JSONObject.toJSONString(object),HashMap.class);
    }

}
