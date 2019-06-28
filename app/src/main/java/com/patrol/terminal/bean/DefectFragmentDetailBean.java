package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class DefectFragmentDetailBean implements Serializable {

    /**
     * content : AB塔腿1米处有一长3米，宽0.4米，深1.5米的坑。
     * start_name : #016
     * end_name : #016
     * find_time : 2015-03-03
     * deal_notes : 回填
     * status : 0
     * deal_dep_name : 和华运维班
     * deal_time : null
     * auditor : null
     * audit_status : 1
     * line_name : 1120新金二线
     * find_user_name : null
     * deal_user_name : null
     * remark : null
     * find_dep_name : null
     * category_name : 杆塔基础
     * grade_name : 一般
     * fileList : []
     * user_name : null
     * dep_name : null
     */

    private String id;
    private Object month_line_id;
    private Object week_id;
    private Object day_id;
    private Object group_id;
    private Object task_id;
    private String category_id;
    private String grade_id;
    private Object patrol_id;
    private String content;
    private String line_id;
    private String start_name;
    private String end_name;
    private String find_time;
    private String deal_notes;
    private String status;
    private String deal_dep_name;
    private String deal_time;
    private String auditor;
    private String audit_status;
    private Object week_line_id;
    private Object day_line_id;
    private Object month_id;
    private Object group_list_id;
    private Object deal_dep_id;
    private String start_id;
    private String end_id;
    private String line_name;
    private Object find_user_id;
    private String find_user_name;
    private Object deal_user_id;
    private String deal_user_name;
    private String remark;
    private Object find_dep_id;
    private String find_dep_name;
    private String category_name;
    private String grade_name;
    private Object user_name;
    private Object dep_name;
    private List<FileBean> fileList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMonth_line_id() {
        return month_line_id;
    }

    public void setMonth_line_id(Object month_line_id) {
        this.month_line_id = month_line_id;
    }

    public Object getWeek_id() {
        return week_id;
    }

    public void setWeek_id(Object week_id) {
        this.week_id = week_id;
    }

    public Object getDay_id() {
        return day_id;
    }

    public void setDay_id(Object day_id) {
        this.day_id = day_id;
    }

    public Object getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Object group_id) {
        this.group_id = group_id;
    }

    public Object getTask_id() {
        return task_id;
    }

    public void setTask_id(Object task_id) {
        this.task_id = task_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public Object getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(Object patrol_id) {
        this.patrol_id = patrol_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeal_dep_name() {
        return deal_dep_name;
    }

    public void setDeal_dep_name(String deal_dep_name) {
        this.deal_dep_name = deal_dep_name;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public Object getWeek_line_id() {
        return week_line_id;
    }

    public void setWeek_line_id(Object week_line_id) {
        this.week_line_id = week_line_id;
    }

    public Object getDay_line_id() {
        return day_line_id;
    }

    public void setDay_line_id(Object day_line_id) {
        this.day_line_id = day_line_id;
    }

    public Object getMonth_id() {
        return month_id;
    }

    public void setMonth_id(Object month_id) {
        this.month_id = month_id;
    }

    public Object getGroup_list_id() {
        return group_list_id;
    }

    public void setGroup_list_id(Object group_list_id) {
        this.group_list_id = group_list_id;
    }

    public Object getDeal_dep_id() {
        return deal_dep_id;
    }

    public void setDeal_dep_id(Object deal_dep_id) {
        this.deal_dep_id = deal_dep_id;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public Object getFind_user_id() {
        return find_user_id;
    }

    public void setFind_user_id(Object find_user_id) {
        this.find_user_id = find_user_id;
    }

    public String getFind_user_name() {
        return find_user_name;
    }

    public void setFind_user_name(String find_user_name) {
        this.find_user_name = find_user_name;
    }

    public Object getDeal_user_id() {
        return deal_user_id;
    }

    public void setDeal_user_id(Object deal_user_id) {
        this.deal_user_id = deal_user_id;
    }

    public String getDeal_user_name() {
        return deal_user_name;
    }

    public void setDeal_user_name(String deal_user_name) {
        this.deal_user_name = deal_user_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getFind_dep_id() {
        return find_dep_id;
    }

    public void setFind_dep_id(Object find_dep_id) {
        this.find_dep_id = find_dep_id;
    }

    public String getFind_dep_name() {
        return find_dep_name;
    }

    public void setFind_dep_name(String find_dep_name) {
        this.find_dep_name = find_dep_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public Object getUser_name() {
        return user_name;
    }

    public void setUser_name(Object user_name) {
        this.user_name = user_name;
    }

    public Object getDep_name() {
        return dep_name;
    }

    public void setDep_name(Object dep_name) {
        this.dep_name = dep_name;
    }

    public List<FileBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileBean> fileList) {
        this.fileList = fileList;
    }
}
