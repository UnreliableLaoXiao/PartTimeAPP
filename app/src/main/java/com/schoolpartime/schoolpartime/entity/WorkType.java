package com.schoolpartime.schoolpartime.entity;

/**
 *
 * 兼职工作类型
 * */
public class WorkType {
    private Integer wtId;
    //兼职类型名称
    private String wtName;

    public Integer getWtId() {
        return wtId;
    }

    public void setWtId(Integer wtId) {
        this.wtId = wtId;
    }

    public String getWtName() {
        return wtName;
    }

    public void setWtName(String wtName) {
        this.wtName = wtName == null ? null : wtName.trim();
    }

	public WorkType(Integer wtId, String wtName) {
		super();
		this.wtId = wtId;
		this.wtName = wtName;
	}

	public WorkType() {
		super();
	}

	@Override
	public String toString() {
		return "WorkType [wtId=" + wtId + ", wtName=" + wtName + "]";
	}
    
}