package com.schoolpartime.schoolpartime.entity;


/**
 * 商家发布的兼职信息
 */
public class WorkInfo {
    private Integer id;   //工作信息编号
    private Integer userid;   //商家编号，外键
    private Integer wtId;   //兼职类型
    private String workTitle;   //招聘信息标题
    private Integer money;   //薪水
    private String createTime;   //发布时间
    private String end_way;   //薪水结算方式
    private String workText;   //工作职责描述
    private String address;   //详细地址
    private String city;   //所在城市(市)
    private String contacts;   //联系人
    private String contactsWay;   //联系人方式

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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
        this.workTitle = workTitle;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEnd_way() {
        return end_way;
    }

    public void setEnd_way(String end_way) {
        this.end_way = end_way;
    }

    public String getWorkText() {
        return workText;
    }

    public void setWorkText(String workText) {
        this.workText = workText;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsWay() {
        return contactsWay;
    }

    public void setContactsWay(String contactsWay) {
        this.contactsWay = contactsWay;
    }

    public WorkInfo(Integer id, Integer userid, Integer wtId, String workTitle, Integer money, String createTime, String end_way, String workText, String address, String city, String contacts, String contactsWay) {
        this.id = id;
        this.userid = userid;
        this.wtId = wtId;
        this.workTitle = workTitle;
        this.money = money;
        this.createTime = createTime;
        this.end_way = end_way;
        this.workText = workText;
        this.address = address;
        this.city = city;
        this.contacts = contacts;
        this.contactsWay = contactsWay;
    }

    public WorkInfo() {
    }

    @Override
    public String toString() {
        return "WorkInfo{" +
                "id=" + id +
                ", userid=" + userid +
                ", wtId=" + wtId +
                ", workTitle='" + workTitle + '\'' +
                ", money=" + money +
                ", createTime='" + createTime + '\'' +
                ", end_way='" + end_way + '\'' +
                ", workText='" + workText + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsWay='" + contactsWay + '\'' +
                '}';
    }
}