package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaobanTech.springcloud.entity.*;
import com.yaobanTech.springcloud.entity.utils.RedisGeneratorCode;
import com.yaobanTech.springcloud.entity.utils.RespBean;
import com.yaobanTech.springcloud.mapper.ComSuperviseMapper;
import com.yaobanTech.springcloud.mapper.ConSuperviseMapper;
import com.yaobanTech.springcloud.mapper.FacSuperviseMapper;
import com.yaobanTech.springcloud.mapper.SpeSuperviseMapper;
import com.yaobanTech.springcloud.service.ISuperviseService;
import com.yaobanTech.springcloud.service.feign.*;
import com.yaobanTech.springcloud.utils.FieldUtils;
import com.yaobanTech.springcloud.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lijh
 * @since 2020-12-18
 */
@Service
public class SuperviseServiceImpl extends ServiceImpl<ComSuperviseMapper, ComSupervise> implements ISuperviseService {

    @Autowired
    private ComSuperviseMapper comSuperviseMapper;
    @Autowired
    private ConSuperviseMapper conSuperviseMapper;
    @Autowired
    private FacSuperviseMapper facSuperviseMapper;
    @Autowired
    private SpeSuperviseMapper speSuperviseMapper;
    @Autowired
    @Lazy
    private RedisGeneratorCode redisGeneratorCode;

    @Override
    public RespBean getComSupervise(long pageNo,long pageSize, Map<String, Object> params,HttpServletRequest request) {
        Map map = (Map) params;
        //根据当前登陆人
        //创建对象
        QueryWrapper<ComSupervise> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(map)){

        }
        List<ComSupervise> list = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        //前端选择全部之后，pagesize就是最大数值
        if(pageSize == 1){
            QueryWrapper<ComSupervise> query = new QueryWrapper<>();
            pageSize = comSuperviseMapper.selectList(query).size();
        }
        IPage<ComSupervise> page = new Page<ComSupervise>(pageNo,pageSize);
        page =comSuperviseMapper.selectPage(page,queryWrapper);
        list= comSuperviseMapper.selectPage(page,queryWrapper).getRecords();
        result.put("total",page.getTotal());
        result.put("list",list);
        return RespBean.ok("").setObj(result);
    }
    @Override
    public RespBean getConSupervise(long pageNo,long pageSize, Map<String, Object> params,HttpServletRequest request) {
        Map map = (Map) params;
        //根据当前登陆人
        //创建对象
        QueryWrapper<ConSupervise> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(map)){

        }
        List<ConSupervise> list = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        //前端选择全部之后，pagesize就是最大数值
        if(pageSize == 1){
            QueryWrapper<ConSupervise> query = new QueryWrapper<>();
            pageSize = conSuperviseMapper.selectList(query).size();
        }
        IPage<ConSupervise> page = new Page<ConSupervise>(pageNo,pageSize);
        page =conSuperviseMapper.selectPage(page,queryWrapper);
        list= conSuperviseMapper.selectPage(page,queryWrapper).getRecords();
        result.put("total",page.getTotal());
        result.put("list",list);
        return RespBean.ok("").setObj(result);
    }
    @Override
    public RespBean getFacSupervise(long pageNo,long pageSize, Map<String, Object> params,HttpServletRequest request) {
        Map map = (Map) params;
        //根据当前登陆人
        //创建对象
        QueryWrapper<FacSupervise> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(map)){

        }
        List<FacSupervise> list = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        //前端选择全部之后，pagesize就是最大数值
        if(pageSize == 1){
            QueryWrapper<FacSupervise> query = new QueryWrapper<>();
            pageSize = facSuperviseMapper.selectList(query).size();
        }
        IPage<FacSupervise> page = new Page<FacSupervise>(pageNo,pageSize);
        page =facSuperviseMapper.selectPage(page,queryWrapper);
        list= facSuperviseMapper.selectPage(page,queryWrapper).getRecords();
        result.put("total",page.getTotal());
        result.put("list",list);
        return RespBean.ok("").setObj(result);
    }
    @Override
    public RespBean getSpeSupervise(long pageNo,long pageSize, Map<String, Object> params,HttpServletRequest request) {
        Map map = (Map) params;
        //根据当前登陆人
        //创建对象
        QueryWrapper<SpeSupervise> queryWrapper = new QueryWrapper<>();
        if(FieldUtils.isObjectNotEmpty(map)){

        }
        List<SpeSupervise> list = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        //前端选择全部之后，pagesize就是最大数值
        if(pageSize == 1){
            QueryWrapper<SpeSupervise> query = new QueryWrapper<>();
            pageSize = speSuperviseMapper.selectList(query).size();
        }
        IPage<SpeSupervise> page = new Page<SpeSupervise>(pageNo,pageSize);
        page =speSuperviseMapper.selectPage(page,queryWrapper);
        list= speSuperviseMapper.selectPage(page,queryWrapper).getRecords();
        result.put("total",page.getTotal());
        result.put("list",list);
        return RespBean.ok("").setObj(result);
    }
    @Override
    @Transactional
    public RespBean saveComSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            ComSupervise comSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), ComSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(comSupervise)){
                String recordId = redisGeneratorCode.createGenerateCode("居民检查编号","JM",true,4);
                comSupervise.setRecordId(recordId);
                comSuperviseMapper.insert(comSupervise);
                return RespBean.ok("添加成功成功！").setObj(comSupervise);
            }else{
                return RespBean.error("参数为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
    @Override
    @Transactional
    public RespBean saveConSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            ConSupervise conSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), ConSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(conSupervise)){
                String recordId = redisGeneratorCode.createGenerateCode("施工检查编号","SG",true,4);
                conSupervise.setRecordId(recordId);
                conSuperviseMapper.insert(conSupervise);
                return RespBean.ok("添加成功成功！").setObj(conSupervise);
            }else{
                return RespBean.error("参数为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
    @Override
    @Transactional
    public RespBean saveFacSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            FacSupervise facSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), FacSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(facSupervise)){
                String recordId = redisGeneratorCode.createGenerateCode("设施检查编号","SS",true,4);
                facSupervise.setRecordId(recordId);
                facSuperviseMapper.insert(facSupervise);
                return RespBean.ok("添加成功成功！").setObj(facSupervise);
            }else{
                return RespBean.error("参数为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
    @Override
    @Transactional
    public RespBean saveSpeSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            SpeSupervise speSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), SpeSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(speSupervise)){
                String recordId = redisGeneratorCode.createGenerateCode("特行检查编号","TX",true,4);
                speSupervise.setRecordId(recordId);
                speSuperviseMapper.insert(speSupervise);
                return RespBean.ok("添加成功成功！").setObj(speSupervise);
            }else{
                return RespBean.error("参数为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }

    @Override
    @Transactional
    public RespBean updateComSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            ComSupervise comSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), ComSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(comSupervise.getRecordId())){
                String recordId = redisGeneratorCode.createGenerateCode("居民检查编号","JM",true,4);
                comSupervise.setRecordId(recordId);
                comSuperviseMapper.insert(comSupervise);
                return RespBean.ok("添加成功成功！").setObj(comSupervise);
            }else{
                return RespBean.error("参数编号为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
    @Override
    @Transactional
    public RespBean updateConSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            ConSupervise conSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), ConSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(conSupervise.getRecordId())){
                String recordId = redisGeneratorCode.createGenerateCode("施工检查编号","SG",true,4);
                conSupervise.setRecordId(recordId);
                conSuperviseMapper.insert(conSupervise);
                return RespBean.ok("添加成功成功！").setObj(conSupervise);
            }else{
                return RespBean.error("参数编号为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
    @Override
    @Transactional
    public RespBean updateFacSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            FacSupervise facSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), FacSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(facSupervise.getRecordId())){
                String recordId = redisGeneratorCode.createGenerateCode("设施检查编号","SS",true,4);
                facSupervise.setRecordId(recordId);
                facSuperviseMapper.insert(facSupervise);
                return RespBean.ok("添加成功成功！").setObj(facSupervise);
            }else{
                return RespBean.error("参数编号为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
    @Override
    @Transactional
    public RespBean updateSpeSupervise(Map<String, Object> params) {
        if(FieldUtils.isObjectNotEmpty(params)){
            SpeSupervise speSupervise = JSONObject.parseObject(JSONObject.toJSONString(params.get("form")), SpeSupervise.class);
            //创建对象
            if(FieldUtils.isObjectNotEmpty(speSupervise.getRecordId())){
                String recordId = redisGeneratorCode.createGenerateCode("特行检查编号","TX",true,4);
                speSupervise.setRecordId(recordId);
                speSuperviseMapper.insert(speSupervise);
                return RespBean.ok("添加成功成功！").setObj(speSupervise);
            }else{
                return RespBean.error("参数编号为空！");
            }
        }else{
            return RespBean.error("map参数为空!");
        }
    }
}
