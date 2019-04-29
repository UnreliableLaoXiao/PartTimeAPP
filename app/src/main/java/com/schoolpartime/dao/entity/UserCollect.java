package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_usercollect")
public class UserCollect {

    long id;
    long user_id;
    long work_id;
    @Generated(hash = 120400750)
    public UserCollect(long id, long user_id, long work_id) {
        this.id = id;
        this.user_id = user_id;
        this.work_id = work_id;
    }
    @Generated(hash = 546062259)
    public UserCollect() {
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

    @Override
    public String toString() {
        return "UserCollect{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", work_id=" + work_id +
                '}';
    }
}
