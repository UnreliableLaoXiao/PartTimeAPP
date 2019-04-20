package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_worktype")
public class WorkType {
    private Integer id;
    //兼职类型名称
    private String name;
    @Generated(hash = 462648108)
    public WorkType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1774383450)
    public WorkType() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}