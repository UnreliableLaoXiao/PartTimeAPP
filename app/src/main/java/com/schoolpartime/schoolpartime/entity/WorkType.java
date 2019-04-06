package com.schoolpartime.schoolpartime.entity;

/**
 *
 * 兼职工作类型
 * */
public class WorkType {
    private Integer id;
    //兼职类型名称
    private String name;

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

    public WorkType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public WorkType() {
    }

    @Override
    public String toString() {
        return "WorkType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}