package com.patrol.terminal.bean;

public class DangerPatrolReqBean {

    private String f_id;
    private String id;
    private int patrol_cycle;
    private String type_id;
    private String close_time;
    private String in_status;
    private String from_user_id;
    private String from_user_name;
    private String find_dep_id;
    private String line_name;
    private String tower_name;

    public String getFind_dep_id() {
        return find_dep_id;
    }

    public void setFind_dep_id(String find_dep_id) {
        this.find_dep_id = find_dep_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public String getIn_status() {
        return in_status;
    }

    public void setIn_status(String in_status) {
        this.in_status = in_status;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatrol_cycle() {
        return patrol_cycle;
    }

    public void setPatrol_cycle(int patrol_cycle) {
        this.patrol_cycle = patrol_cycle;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }
}
