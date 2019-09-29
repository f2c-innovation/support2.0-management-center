package com.fit2cloud.support.model;

import java.util.ArrayList;
import java.util.List;

public class SupportTreeNode {

    private String id;
    private String name;
    private int order = 0;

    private List<SupportTreeNode> children = new ArrayList<>();

    private boolean checked = false;

    private String type;

    private boolean disabled = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<SupportTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<SupportTreeNode> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
