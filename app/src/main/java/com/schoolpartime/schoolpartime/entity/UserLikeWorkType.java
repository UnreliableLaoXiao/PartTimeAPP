package com.schoolpartime.schoolpartime.entity;


/**
 * 普通用户喜好的兼职类型
 */
public class UserLikeWorkType {
    private long userId;
    //喜欢类型1
    private int like1;

    //喜欢类型2
    private int like2;

    //喜欢类型3
    private int like3;

    public UserLikeWorkType(long userId, int like1, int like2, int like3) {
        this.userId = userId;
        this.like1 = like1;
        this.like2 = like2;
        this.like3 = like3;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLike1() {
        return like1;
    }

    public void setLike1(int like1) {
        this.like1 = like1;
    }

    public int getLike2() {
        return like2;
    }

    public void setLike2(int like2) {
        this.like2 = like2;
    }

    public int getLike3() {
        return like3;
    }

    public void setLike3(int like3) {
        this.like3 = like3;
    }

    public UserLikeWorkType() {
    }

    @Override
    public String toString() {
        return "UserLikeWorkType{" +
                "userId=" + userId +
                ", like1=" + like1 +
                ", like2=" + like2 +
                ", like3=" + like3 +
                '}';
    }
}