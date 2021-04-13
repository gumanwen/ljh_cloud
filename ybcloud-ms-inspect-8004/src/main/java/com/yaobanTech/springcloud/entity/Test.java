package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_Test")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "gid")
    private Integer gid;
    private Integer source;
    private Integer target;
    private Integer sid;
    private Integer svid;
    private Integer tvid;
    private Integer status;
    private int pid;
    @TableField(exist = false)
    private List<Test> list;
    public Test() {
    }

    public Test(Integer gid, Integer source, Integer target, Integer sid, Integer svid, Integer tvid, Integer status, int pid) {
        this.gid = gid;
        this.source = source;
        this.target = target;
        this.sid = sid;
        this.svid = svid;
        this.tvid = tvid;
        this.status = status;
        this.pid = pid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getSvid() {
        return svid;
    }

    public void setSvid(Integer svid) {
        this.svid = svid;
    }

    public Integer getTvid() {
        return tvid;
    }

    public void setTvid(Integer tvid) {
        this.tvid = tvid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<Test> getList() {
        return list;
    }

    public void setList(List<Test> list) {
        this.list = list;
    }
}
