package com.patrol.terminal.bean;

import java.io.Serializable;

public class SpecialAttrList implements Serializable {

    /**
     * id : 98F764466504450DBC4C490F6DB512C2
     * name : 八防
     * p_id : 0
     * sort : 1
     * remarks : null
     * level_no : 1
     * p_name : null
     * full_name : null
     * leaf : null
     * leaf_total : null
     */

    private String id;
    private String name;
    private String p_id;
    private int sort;
    private Object remarks;
    private int level_no;
    private Object p_name;
    private Object full_name;
    private Object leaf;
    private Object leaf_total;

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

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public int getLevel_no() {
        return level_no;
    }

    public void setLevel_no(int level_no) {
        this.level_no = level_no;
    }

    public Object getP_name() {
        return p_name;
    }

    public void setP_name(Object p_name) {
        this.p_name = p_name;
    }

    public Object getFull_name() {
        return full_name;
    }

    public void setFull_name(Object full_name) {
        this.full_name = full_name;
    }

    public Object getLeaf() {
        return leaf;
    }

    public void setLeaf(Object leaf) {
        this.leaf = leaf;
    }

    public Object getLeaf_total() {
        return leaf_total;
    }

    public void setLeaf_total(Object leaf_total) {
        this.leaf_total = leaf_total;
    }
}
