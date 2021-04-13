package com.yaobanTech.springcloud.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author zhoufei
 * Created by zhoufei on 2018/3/6.
 */
@Data
@Accessors(chain = true)
@ToString
public class PermissionInfo {
    private Integer id;

    private String name;

    private String type;

    private String permissionValue;

    private Integer parentId;

    private Integer treeId;

    private Integer pageId;

    private String icon;

    /**
     * 菜单路径
     */
    private String uri;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否是外部菜单
     */
    private Integer isExternal;

    /**
     * 外部资源路径
     */
    private String externalUri;
}
