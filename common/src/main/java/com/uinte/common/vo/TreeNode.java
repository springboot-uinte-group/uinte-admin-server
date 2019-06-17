package com.uinte.common.vo;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    protected String id;
    protected String parentId;
    List<TreeNode> children = new ArrayList<TreeNode>();
    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void add(TreeNode node){
        children.add(node);
    }
}
