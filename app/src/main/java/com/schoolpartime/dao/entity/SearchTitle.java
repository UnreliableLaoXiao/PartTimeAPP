package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "t_search_title")
public class SearchTitle {

    @Property(nameInDb = "_id")
    long _id;
    @Property(nameInDb = "title")
    String title;
    @Generated(hash = 1840585531)
    public SearchTitle(long _id, String title) {
        this._id = _id;
        this.title = title;
    }
    @Generated(hash = 242580151)
    public SearchTitle() {
    }
    public long get_id() {
        return this._id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }



}
