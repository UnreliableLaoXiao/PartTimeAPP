package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_user_request")
public class RequestWork {

    long id;

    long user_id;

    long work_id;

    int statu;

    @Generated(hash = 801567420)
    public RequestWork(long id, long user_id, long work_id, int statu) {
        this.id = id;
        this.user_id = user_id;
        this.work_id = work_id;
        this.statu = statu;
    }

    @Generated(hash = 699760911)
    public RequestWork() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getWork_id() {
        return this.work_id;
    }

    public void setWork_id(long work_id) {
        this.work_id = work_id;
    }

    public int getStatu() {
        return this.statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }


}
