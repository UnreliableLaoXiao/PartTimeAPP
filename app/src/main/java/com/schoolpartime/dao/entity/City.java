package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_citys")
public class City {
    int id;
    String cityName;
    @Generated(hash = 1906437094)
    public City(int id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }
    @Generated(hash = 750791287)
    public City() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
