package com.schoolpartime.schoolpartime.entity.baseModel;

public class ResultModel<T> {
    public String message;
    public T data;
    public String type;
    public int code;

    public ResultModel(String message, T data, String type, int code) {
        this.message = message;
        this.data = data;
        this.type = type;
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                ", code=" + code +
                '}';
    }

    public ResultModel() {
    }
}
