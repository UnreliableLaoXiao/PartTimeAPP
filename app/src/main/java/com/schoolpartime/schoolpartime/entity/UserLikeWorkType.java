package com.schoolpartime.schoolpartime.entity;


/**
 * 普通用户喜好的兼职类型
 */
public class UserLikeWorkType {
    private Integer ulwtId;
    //用户编号，外键
    private Integer userId;
    //兼职类型
    private Integer wtId;

    public Integer getUlwtId() {
        return ulwtId;
    }

    public void setUlwtId(Integer ulwtId) {
        this.ulwtId = ulwtId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWtId() {
        return wtId;
    }

    public void setWtId(Integer wtId) {
        this.wtId = wtId;
    }

	public UserLikeWorkType(Integer ulwtId, Integer userId, Integer wtId) {
		super();
		this.ulwtId = ulwtId;
		this.userId = userId;
		this.wtId = wtId;
	}

	public UserLikeWorkType() {
		super();
	}

	@Override
	public String toString() {
		return "UserLikeWorkType [ulwtId=" + ulwtId + ", userId=" + userId
				+ ", wtId=" + wtId + "]";
	}
    
}