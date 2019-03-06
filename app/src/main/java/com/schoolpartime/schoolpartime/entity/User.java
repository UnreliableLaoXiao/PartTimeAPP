package com.schoolpartime.schoolpartime.entity;

public class User {
    private int id;

    private String username;

    private String password;

    private int verify_psw;

    private int type;

    public User(int id, String username, String password, int verify_psw, int type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.verify_psw = verify_psw;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVerify_psw() {
        return verify_psw;
    }

    public void setVerify_psw(int verify_psw) {
        this.verify_psw = verify_psw;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
