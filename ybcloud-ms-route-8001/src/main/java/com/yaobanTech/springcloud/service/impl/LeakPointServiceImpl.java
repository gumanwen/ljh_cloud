package com.yaobanTech.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaobanTech.springcloud.ToolUtils.DateFormatUtils;
import com.yaobanTech.springcloud.ToolUtils.UrlUtils;
import com.yaobanTech.springcloud.domain.*;
import com.yaobanTech.springcloud.repository.BizLeakPointRepository;
import com.yaobanTech.springcloud.repository.BizSignPointMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeakPointServiceImpl {
    @Autowired
    @Lazy
    private BizLeakPointRepository leakPointRepository;

    @Autowired
    @Lazy
    private BizSignPointMapper bizSignPointMapper;

    @Autowired
    @Lazy
    private RouteServiceImpl routeService;

    @Autowired
    @Lazy
    private OauthService oauthService;

    @Autowired
    @Lazy
    private FileService fileService;

    @Autowired
    @Lazy
    private RedisService redisService;

    @Autowired
    private UrlUtils urlUtils;

    @Value("${qyserver.orderIp}")
    private String orderIp;

    @GlobalTransactional
    public RespBean saveLeakPoint(String param, MultipartFile[] fileList,HttpServletRequest request) {
        BizLeakPointEntity bizLeakPointEntity = JSONObject.parseObject(param, BizLeakPointEntity.class);
        String type = "ldfj";
        if(bizLeakPointEntity != null) {
            try {
                String leakPointCode = null;
                LoginUser u = urlUtils.getAll(request);
                String user = u.getLoginname();
                String deptName = u.getDeptName();
                if(deptName.contains("城南")){
                    leakPointCode =redisService.createGenerateCode("隐患点","CNYH",true,2);
                }
                else if(deptName.contains("城北")){
                    leakPointCode =redisService.createGenerateCode("隐患点","CBYH",true,2);
                }
                else if(deptName.contains("石角")){
                    leakPointCode =redisService.createGenerateCode("隐患点","SJYH",true,2);
                }else{
                    return RespBean.error("用水管理所参数不符合系统约定，生成编号异常！");
                }
                bizLeakPointEntity.setCommitDate(DateFormatUtils.DateToStr(new Date()));
                bizLeakPointEntity.setEnabled(1);
                bizLeakPointEntity.setCommitBy(user);
                bizLeakPointEntity.setLeakPointStatus("53");
                bizLeakPointEntity.setLeakPointCode(leakPointCode);
                leakPointRepository.save(bizLeakPointEntity);
                fileService.saveByPid(fileList,bizLeakPointEntity.getLeakPointCode(),type) ;
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("保存失败！");
            }
        }else{
            return RespBean.error("数据为空！");
        }
        return RespBean.ok("保存成功！");
    }

    @Transactional
    public RespBean updateBizLeakPointEntity(HashMap<String,Object> param) {
        BizLeakPointEntity bizLeakPointEntity = null;
        if(!param.isEmpty()) {
            bizLeakPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param), BizLeakPointEntity.class);
            if(bizLeakPointEntity.getId() != null){
            try {
                leakPointRepository.save(bizLeakPointEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("修改失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("修改成功！");
        }else{
            return RespBean.error("参数为空！");
        }
    }

    @Transactional
    public RespBean ignore(String leakPointCode) {
        if(leakPointCode != null) {
             leakPointRepository.updateLeakPointStatus(leakPointCode);
             return RespBean.ok("修改成功！");
        }else{
            return RespBean.error("Id为空！");
        }
    }

    public RespBean deleteBizLeakPointEntity(Integer id) {
        if(id != null) {
            try { leakPointRepository.deleteBizLeakPointEntity(id);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("删除失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("删除成功！");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespBean findDetail(Integer id,HttpServletRequest request) {
        BizLeakPointEntity blpe = null;
        String type = "ldfj";
        if(id != null) {
            try {
                blpe = leakPointRepository.findBizLeakPointEntity(id);
                String user = blpe.getCommitBy();
                String chineseName = (String)urlUtils.getNameByUsername(user,request);
                HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(blpe.getLeakPointStatus()).getObj();
                HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(blpe.getAbnormalPhenomena()).getObj();
                blpe.setCommitByCN(chineseName);
                blpe.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                blpe.setLeakPointStatusEnum(leakPointStatusEnum);
                RespBean bean = fileService.selectOneByPid(blpe.getLeakPointCode(), type);
                List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>) bean.getObj();
                blpe.setFileList(maps);
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("查询失败！");
            }
        }else{
            return RespBean.error("id为空！");
        }
        return RespBean.ok("查询成功！",blpe);
    }

     @Transactional(propagation= Propagation.NOT_SUPPORTED)
     public RespBean findListByUser(HttpServletRequest request) throws UnsupportedEncodingException {
        LoginUser u = urlUtils.getAll(request);
        String user = u.getLoginname();
        String chineseName = u.getName();
         String role = u.getRoleLists();
         List<BizLeakPointEntity> list = null;
         if(!"".equals(role) && role !=null && role.contains("BZZ")){
             list = leakPointRepository.findAll();
         }
         else{
             list = leakPointRepository.findOfList(user);
         }
        if(!list.isEmpty()){
            for (int i = 0; i < list.size(); i++) {
                BizLeakPointEntity bizLeakPointEntity = list.get(i);
                HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getLeakPointStatus()).getObj();
                HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getAbnormalPhenomena()).getObj();
                HashMap<String,Object> assetTypeEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getAssetType()).getObj();

                bizLeakPointEntity.setCommitByCN(chineseName);
                bizLeakPointEntity.setLeakPointStatusEnum(leakPointStatusEnum);
                bizLeakPointEntity.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                bizLeakPointEntity.setAssetTypeEnum(assetTypeEnum);
                //获取报建文件列表
                RespBean respBean = fileService.selectOneByPid(bizLeakPointEntity.getLeakPointCode(), "ldfj");
                List<HashMap<String, Object>> fileList = (List<HashMap<String, Object>>) respBean.getObj();
                if(respBean.getStatus() == 500){
                    throw new RuntimeException("Feign调用文件服务失败");
                }
                bizLeakPointEntity.setFileList(fileList);
            }
        }
         List<BizLeakPointEntity> collect = list.stream().sorted(Comparator.comparing(BizLeakPointEntity::getEnabled).reversed().thenComparing(BizLeakPointEntity::getCommitDate).reversed()).collect(Collectors.toList());
         return RespBean.ok("查询成功！",collect);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
     public RespBean findCondition(LeakPointQuery leakPointQuery,HttpServletRequest request) throws UnsupportedEncodingException {
        HashMap<String,Object> hashMap = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        List<BizLeakPointEntity> list = new ArrayList<>();
        if(leakPointQuery != null){
            LoginUser u = urlUtils.getAll(request);
            String user = u.getLoginname();
            String chineseName = u.getName();
            list = bizSignPointMapper.leakPointQuery(leakPointQuery);
            if(!list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    BizLeakPointEntity bizLeakPointEntity = list.get(i);
                    HashMap<String,Object> leakPointStatusEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getLeakPointStatus()).getObj();
                    HashMap<String,Object> abnormalPhenomenaEnum = (HashMap)routeService.findEnum(bizLeakPointEntity.getAbnormalPhenomena()).getObj();
                    bizLeakPointEntity.setCommitByCN(chineseName);
                    bizLeakPointEntity.setLeakPointStatusEnum(leakPointStatusEnum);
                    bizLeakPointEntity.setAbnormalPhenomenaEnum(abnormalPhenomenaEnum);
                    codeList.add(bizLeakPointEntity.getLeakPointCode());
                }
                hashMap.put("LeakPointList",list);
                hashMap.put("CodeList",codeList);
            }
       }else {
            return RespBean.error("查询条件为空！");
        }
        return RespBean.ok("查询成功！",hashMap);
    }

    @Transactional
    public RespBean transferToOrder(@RequestBody HashMap<String,Object> param) throws IOException {
        BizLeakPointEntity bizLeakPointEntity = null;
        if (!param.isEmpty()) {
            bizLeakPointEntity = JSONObject.parseObject(JSONObject.toJSONString(param), BizLeakPointEntity.class);
            if (bizLeakPointEntity.getId() != null) {
                /*try {*/
                    // 1. 得到访问地址的URL
                    URL url = new URL(String.valueOf(orderIp));
                    // 2. 得到网络访问对象java.net.HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
                    // 设置是否向HttpURLConnection输出
                    connection.setDoOutput(true);
                    // 设置是否从httpUrlConnection读入
                    connection.setDoInput(true);
                    // 设置请求方式
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
                    // 设置是否使用缓存
                    connection.setUseCaches(true);
                    // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                    connection.setInstanceFollowRedirects(true);
                    // 设置超时时间
                    connection.setConnectTimeout(30000);
                    // 连接
                    connection.connect();
                    // 得到请求的输出流对象
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                    String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "  <soap:Body>\n" +
                            "    <GetHotlineInfo xmlns=\"http://tempuri.org/\">\n" +
                            "    <hotinputinfo>\n" +
                            "    <Uniqueid>"+bizLeakPointEntity.getLeakPointCode()+"</Uniqueid>\n" +
                            "    <Accepttime>"+bizLeakPointEntity.getCommitDate()+"</Accepttime>\n" +
                            "    <Reportpeople>"+bizLeakPointEntity.getCommitByCN()+"</Reportpeople>\n" +
                            "    <Happenaddr>"+bizLeakPointEntity.getAddress()+"</Happenaddr>\n" +
                            "    <Tel></Tel>\n" +
                            "    <Mobile></Mobile>\n" +
                            "    <Reportsource></Reportsource>\n" +
                            "    <Reporttype>故障报修测试</Reporttype>\n" +
                            "    <Reporttpye2>阀门测试</Reporttpye2>\n" +
                            "    <Reportcontent></Reportcontent>\n" +
                            "    <Issecret>false</Issecret>\n" +
                            "    <Acceptremark></Acceptremark>\n" +
                            "    <Isreply></Isreply>\n" +
                            "    <Happentime>"+bizLeakPointEntity.getCommitDate()+"</Happentime>\n" +
                            "    <Sendstation></Sendstation>\n" +
                            "    <Sendtime></Sendtime>\n" +
                            "    <Sendremark></Sendremark>\n" +
                            "    <Cometime_Plan></Cometime_Plan>\n" +
                            "    <Solvetime_Plan></Solvetime_Plan>\n" +
                            "    <IsFuJian></IsFuJian>\n" +
                            "    <Frequency></Frequency>\n" +
                            "    <Time></Time>\n" +
                            "    <AppointmentTime></AppointmentTime>\n" +
                            "    <Sendstation2></Sendstation2>\n" +
                            "    </hotinputinfo>\n" +
                            "    </GetHotlineInfo>\n" +
                            "  </soap:Body>\n" +
                            "</soap:Envelope>\n";
                    writer.write(str);
                    writer.flush();
                    // 4. 得到响应状态码的返回值 responseCode
                    int code = connection.getResponseCode();
                    // 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
                    String msg = "";
                    if (code == 200) {
                        // 正常响应
                        // 从流中读取响应信息
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                        String line = null;
                        while ((line = reader.readLine()) != null) { // 循环从流中读取
                            msg += line + "\n";
                        }
                        reader.close(); // 关闭流
                    }
                    System.out.println("当前用户的code是:" + code);
                    // 6. 断开连接，释放资源
                    connection.disconnect();
                    bizLeakPointEntity.setIsTransfer("1");
                    leakPointRepository.save(bizLeakPointEntity);
                /*} catch (IOException e) {
                    e.printStackTrace();
                    return RespBean.error("转工单失败！");
                }*/
            }
            return RespBean.ok("转工单成功！");
        } else {
            return RespBean.error("缺少参数，转工单失败！");
        }
    }
}
