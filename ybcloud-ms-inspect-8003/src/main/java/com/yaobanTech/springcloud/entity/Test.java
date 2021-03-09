package com.yaobanTech.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@TableName("BIZ_Test")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "gid")
    private Integer gid;
    private Integer source;
    private Integer target;
    private Integer sid;
    public Test() {
    }

    public Test(Integer gid, Integer source, Integer target, Integer sid) {
        this.gid = gid;
        this.source = source;
        this.target = target;
        this.sid = sid;
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
}
