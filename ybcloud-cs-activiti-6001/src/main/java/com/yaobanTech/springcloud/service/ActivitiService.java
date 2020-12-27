package com.yaobanTech.springcloud.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
@Service
public class ActivitiService {

    @Transactional
    public HashMap<Object,Object> deployment(String deploymentId, String deploymentName) {
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
        return map;
    }
}
