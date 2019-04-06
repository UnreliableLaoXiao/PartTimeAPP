package com.schoolpartime.schoolpartime.entity;

public class Message {
    String msg_mes;

    long msg_from;

    long msg_to;

    int msg_type;

    int msg_state;

    public String getMsg_mes() {
        return msg_mes;
    }

    public void setMsg_mes(String msg_mes) {
        this.msg_mes = msg_mes;
    }

    public long getMsg_from() {
        return msg_from;
    }

    public void setMsg_from(long msg_from) {
        this.msg_from = msg_from;
    }

    public long getMsg_to() {
        return msg_to;
    }

    public void setMsg_to(long msg_to) {
        this.msg_to = msg_to;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public int getMsg_state() {
        return msg_state;
    }

    public void setMsg_state(int msg_state) {
        this.msg_state = msg_state;
    }

    public Message() {
    }

    public Message(String msg_mes, long msg_from, long msg_to, int msg_type, int msg_state) {
        this.msg_mes = msg_mes;
        this.msg_from = msg_from;
        this.msg_to = msg_to;
        this.msg_type = msg_type;
        this.msg_state = msg_state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg_mes='" + msg_mes + '\'' +
                ", msg_from=" + msg_from +
                ", msg_to=" + msg_to +
                ", msg_type=" + msg_type +
                ", msg_state=" + msg_state +
                '}';
    }
}
