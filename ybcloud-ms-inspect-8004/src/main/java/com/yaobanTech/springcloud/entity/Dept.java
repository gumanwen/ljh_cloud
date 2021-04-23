package com.yaobanTech.springcloud.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhoufei
 * @version 1.0
 * @description 部门实体类
 * @date 2018/10/29 17:18
 */
@Data
@ToString
public class Dept {
    /**
     * null
     */
    private Integer id;

    /**
     * 集团ID（预留数据隔离）
     */
    private Integer groupId;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 部门等级
     */
    private String level;

    /**
     * 部门类别
     */
    private String type;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门别名
     */
    private String aliasName;

    /**
     * 部门描述
     */
    private String desc;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * null
     */
    private Date createTime;

    /**
     * null
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 更新人ID
     */
    private Integer updateUserId;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 是否删除 ‘1‘代表删除 ’0‘代表未删除
     */
    private Boolean isDelete;

    /**
     * 组织类型（0：平台用户组织  1：管理所组织  2：外业）
     */
    private String deptType;

}