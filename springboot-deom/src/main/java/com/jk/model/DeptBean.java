package com.jk.model;

import java.util.List;

public class DeptBean {
    private Integer id;
    private String  text;
    private Integer pid;
    private List<DeptBean> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<DeptBean> getChildren() {
        return children;
    }

    public void setChildren(List<DeptBean> children) {
        this.children = children;
    }
}
