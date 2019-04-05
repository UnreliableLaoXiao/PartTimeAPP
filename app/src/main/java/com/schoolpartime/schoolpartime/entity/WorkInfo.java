package com.schoolpartime.schoolpartime.entity;


/**
 * 商家发布的兼职信息
 */
public class WorkInfo {
    private Integer swId;
    //商家编号，外键
    private Integer sellerId;
    //商家信息编号,外键
    private Integer siId;
    //兼职类型
    private Integer wtId;
    //招聘信息标题
    private String workTitle;
    //性别要求
    private String sexAsk;
    //需要人数
    private Integer peopleNumber;
    //工作时间
    private String needTime;
    //薪水
    private Integer money;
    //薪水结算方式
    private String salaryEnd;
    //所在城市
    private String city;
    //详细地址
    private String address;
    //工作职责描述
    private String workText;
    //联系人
    private String contacts;
    //联系人方式
    private String contactsWay;
    //发布时间
    private String createTime;

    public Integer getSwId() {
        return swId;
    }

    public void setSwId(Integer swId) {
        this.swId = swId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getSiId() {
        return siId;
    }

    public void setSiId(Integer siId) {
        this.siId = siId;
    }

    public Integer getWtId() {
        return wtId;
    }

    public void setWtId(Integer wtId) {
        this.wtId = wtId;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle == null ? null : workTitle.trim();
    }

    public String getSexAsk() {
        return sexAsk;
    }

    public void setSexAsk(String sexAsk) {
        this.sexAsk = sexAsk;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getNeedTime() {
        return needTime;
    }

    public void setNeedTime(String needTime) {
        this.needTime = needTime == null ? null : needTime.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getSalaryEnd() {
        return salaryEnd;
    }

    public void setSalaryEnd(String salaryEnd) {
        this.salaryEnd = salaryEnd == null ? null : salaryEnd.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWorkText() {
        return workText;
    }

    public void setWorkText(String workText) {
        this.workText = workText == null ? null : workText.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getContactsWay() {
        return contactsWay;
    }

    public void setContactsWay(String contactsWay) {
        this.contactsWay = contactsWay == null ? null : contactsWay.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

	public WorkInfo(Integer swId, Integer sellerId, Integer siId,
                    Integer wtId, String workTitle, String sexAsk,
                    Integer peopleNumber, String needTime, Integer money,
                    String salaryEnd, String city, String address, String workText,
                    String contacts, String contactsWay, String createTime) {
		super();
		this.swId = swId;
		this.sellerId = sellerId;
		this.siId = siId;
		this.wtId = wtId;
		this.workTitle = workTitle;
		this.sexAsk = sexAsk;
		this.peopleNumber = peopleNumber;
		this.needTime = needTime;
		this.money = money;
		this.salaryEnd = salaryEnd;
		this.city = city;
		this.address = address;
		this.workText = workText;
		this.contacts = contacts;
		this.contactsWay = contactsWay;
		this.createTime = createTime;
	}

	public WorkInfo() {
		super();
	}

	@Override
	public String toString() {
		return "SellerWork [swId=" + swId + ", sellerId=" + sellerId
				+ ", siId=" + siId + ", wtId=" + wtId + ", workTitle="
				+ workTitle + ", sexAsk=" + sexAsk + ", peopleNumber="
				+ peopleNumber + ", needTime=" + needTime + ", money=" + money
				+ ", salaryEnd=" + salaryEnd + ", city=" + city + ", address="
				+ address + ", workText=" + workText + ", contacts=" + contacts
				+ ", contactsWay=" + contactsWay + ", createTime=" + createTime
				+ "]";
	}
    
}