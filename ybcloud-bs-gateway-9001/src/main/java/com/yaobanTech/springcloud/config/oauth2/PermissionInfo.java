package com.yaobanTech.springcloud.config.oauth2;


/**
 * @author zhoufei
 * Created by zhoufei on 2018/3/6.
 */
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

    public PermissionInfo() {
    }

    public PermissionInfo(Integer id, String name, String type, String permissionValue, Integer parentId, Integer treeId, Integer pageId, String icon, String uri, Integer orderNum, Integer isExternal, String externalUri) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.permissionValue = permissionValue;
        this.parentId = parentId;
        this.treeId = treeId;
        this.pageId = pageId;
        this.icon = icon;
        this.uri = uri;
        this.orderNum = orderNum;
        this.isExternal = isExternal;
        this.externalUri = externalUri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getIsExternal() {
        return isExternal;
    }

    public void setIsExternal(Integer isExternal) {
        this.isExternal = isExternal;
    }

    public String getExternalUri() {
        return externalUri;
    }

    public void setExternalUri(String externalUri) {
        this.externalUri = externalUri;
    }
}
