package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.entity.utils.RespBean;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActivitiService {

    @Transactional
    public RespBean deployment(String deploymentId, String deploymentName) {
        //1,创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        //2,获取RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3,部署对象
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("BPMN/"+deploymentId+".bpmn")// bpmn文 件
                .addClasspathResource("BPMN/"+deploymentId+".png")// 图 片 文 件
                .name(deploymentName)
                .deploy();
        HashMap<Object,Object> map = new HashMap<Object,Object>();
        map.put("流程部署id",deployment.getId());
        map.put("流程部署名称",deployment.getName());
        return RespBean.ok("部署成功，请勿重复部署！").setObj(map);
    }
    @Transactional
    public RespBean startProcess(Map<String,Object> params) {
        String key = (String) params.get("key");
        String businessKey = (String) params.get("businessKey");
        Map<String, Object> variable = new HashMap<String, Object>();
        if (!"".equals(params.get("variable"))) {
            variable = (Map<String, Object>) params.get("variable");
        }
        //1,创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 获取RunTimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key,businessKey,variable);
        return RespBean.ok("启动成功！").setObj(processInstance);
    }
    @Transactional
    public RespBean completeTask(Map<String, Object> params) {
        //String currentPeople =autoDisUtils.getZhName();
        String currentPeople = (String) params.get("username");
        String taskid = (String) params.get("taskid");
        Map<String, Object> variable = new HashMap<String, Object>();
        if (!"".equals(params.get("variable"))) {
            variable = (Map<String, Object>) params.get("variable");
        }
        //创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 创建TaskService
        TaskService taskService = processEngine.getTaskService();
        //处理任务，结合当前用户
        Task task = taskService.createTaskQuery().taskId(taskid).taskAssignee(currentPeople).singleResult();
        if(task != null) {
            taskService.complete(taskid,variable);
            return RespBean.ok(currentPeople+"完成任务："+taskid).setObj(task);
        }else{
            return RespBean.ok(currentPeople+"没有该任务："+taskid).setObj(task);
        }
    }
}
