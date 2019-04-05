package com.schoolpartime.schoolpartime.entity;

public class ChatRecord {

    /**
     * 聊天记录列表
     */

    long id;   //持有人
    private String img;   //头像
    private String name;   //聊天对象名字
    private String new_mes;   //最新消息
    private long other_id;   //聊天对象id
    private int state;
    private String Date;


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOther_id() {
        return other_id;
    }

    public void setOther_id(long other_id) {
        this.other_id = other_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMes() {
        return new_mes;
    }

    public void setMes(String new_mes) {
        this.new_mes = new_mes;
    }
}
