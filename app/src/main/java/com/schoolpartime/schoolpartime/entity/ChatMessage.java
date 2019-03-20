package com.schoolpartime.schoolpartime.entity;

public class ChatMessage {

    private int img;
    private String name;
    private String mes;
    private String dete;

    public String getDete() {
        return dete;
    }

    public void setDete(String dete) {
        this.dete = dete;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
