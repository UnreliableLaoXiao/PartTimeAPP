package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChatRecords {

    /**
     * 聊天记录列表
     */

    long id;   //持有人
    private String img;   //头像
    private String name;   //聊天对象名字
    private String new_mes;   //最新消息
    private long other_id;   //聊天对象id
    private int state;
    private String rcd_date;
    private int noread;
    @Generated(hash = 1487829586)
    public ChatRecords(long id, String img, String name, String new_mes,
            long other_id, int state, String rcd_date, int noread) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.new_mes = new_mes;
        this.other_id = other_id;
        this.state = state;
        this.rcd_date = rcd_date;
        this.noread = noread;
    }
    @Generated(hash = 128109854)
    public ChatRecords() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNew_mes() {
        return this.new_mes;
    }
    public void setNew_mes(String new_mes) {
        this.new_mes = new_mes;
    }
    public long getOther_id() {
        return this.other_id;
    }
    public void setOther_id(long other_id) {
        this.other_id = other_id;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getRcd_date() {
        return this.rcd_date;
    }
    public void setRcd_date(String rcd_date) {
        this.rcd_date = rcd_date;
    }
    public int getNoread() {
        return this.noread;
    }
    public void setNoread(int noread) {
        this.noread = noread;
    }

}
