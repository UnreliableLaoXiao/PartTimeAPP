package com.schoolpartime.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {
    private Long id;

    private String username;

    private int userage;

    private String usersex;

    private String address;

    private String phonenumber;

    private int type;

    @Generated(hash = 127907657)
    public UserInfo(Long id, String username, int userage, String usersex,
            String address, String phonenumber, int type) {
        this.id = id;
        this.username = username;
        this.userage = userage;
        this.usersex = usersex;
        this.address = address;
        this.phonenumber = phonenumber;
        this.type = type;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userage=" + userage +
                ", usersex='" + usersex + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", type=" + type +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserage() {
        return this.userage;
    }

    public void setUserage(int userage) {
        this.userage = userage;
    }

    public String getUsersex() {
        return this.usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
