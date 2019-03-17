package com.schoolpartime.schoolpartime.entity;

public class User {
    private long id;

    private String username;

    private String password;

    private String verifypsw;

    private int type;

    public User(long id, String username, String password, String verifypsw, int type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.verifypsw = verifypsw;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getverifypsw() {
        return verifypsw;
    }

    public void setverifypsw(String verifypsw) {
        this.verifypsw = verifypsw;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
