package com.schoolpartime.schoolpartime.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商家发布的兼职信息
 */
public class WorkInfo implements Parcelable {
    long id;   //工作信息编号
    long bossId;   //商家编号，外键
    int workTypeId;   //兼职类型
    String workTitle;   //招聘信息标题
    String money;   //薪水
    String createTime;   //发布时间
    String end_way;   //薪水结算方式
    String workContext;   //工作职责描述
    String address;   //详细地址
    String city;   //所在城市(市)
    String contacts;   //联系人
    String contactsWay;   //联系人方式
    int workStatu;  //兼职状态

    public WorkInfo(long id, long bossId, int workTypeId, String workTitle, String money, String createTime, String end_way, String workContext, String address, String city, String contacts, String contactsWay, int workStatu) {
        this.id = id;
        this.bossId = bossId;
        this.workTypeId = workTypeId;
        this.workTitle = workTitle;
        this.money = money;
        this.createTime = createTime;
        this.end_way = end_way;
        this.workContext = workContext;
        this.address = address;
        this.city = city;
        this.contacts = contacts;
        this.contactsWay = contactsWay;
        this.workStatu = workStatu;
    }

    public WorkInfo() {
    }

    protected WorkInfo(Parcel in) {
        id = in.readLong();
        bossId = in.readLong();
        workTypeId = in.readInt();
        workTitle = in.readString();
        money = in.readString();
        createTime = in.readString();
        end_way = in.readString();
        workContext = in.readString();
        address = in.readString();
        city = in.readString();
        contacts = in.readString();
        contactsWay = in.readString();
        workStatu = in.readInt();
    }

    public static final Creator<WorkInfo> CREATOR = new Creator<WorkInfo>() {
        @Override
        public WorkInfo createFromParcel(Parcel in) {
            return new WorkInfo(in);
        }

        @Override
        public WorkInfo[] newArray(int size) {
            return new WorkInfo[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBossId() {
        return bossId;
    }

    public void setBossId(long bossId) {
        this.bossId = bossId;
    }

    public int getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(int workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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

    public String getWorkContext() {
        return workContext;
    }

    public void setWorkContext(String workContext) {
        this.workContext = workContext;
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

    public int getWorkStatu() {
        return workStatu;
    }

    public void setWorkStatu(int workStatu) {
        this.workStatu = workStatu;
    }

    @Override
    public String toString() {
        return "WorkInfo{" +
                "  id=" + id +
                ", bossId=" + bossId +
                ", workTypeId=" + workTypeId +
                ", workTitle='" + workTitle + '\'' +
                ", money='" + money + '\'' +
                ", createTime='" + createTime + '\'' +
                ", end_way='" + end_way + '\'' +
                ", workContext='" + workContext + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsWay='" + contactsWay + '\'' +
                ", workStatu=" + workStatu +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(bossId);
        dest.writeInt(workTypeId);
        dest.writeString(workTitle);
        dest.writeString(money);
        dest.writeString(createTime);
        dest.writeString(end_way);
        dest.writeString(workContext);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(contacts);
        dest.writeString(contactsWay);
        dest.writeInt(workStatu);
    }

    Parcelable.Creator<WorkInfo> creator = new Creator<WorkInfo>() {
        @Override
        public WorkInfo createFromParcel(Parcel source) {
            WorkInfo workInfo = new WorkInfo();
            workInfo.id = source.readLong();
            workInfo.bossId = source.readLong();
            workInfo.workTypeId = source.readInt();
            workInfo.workTitle = source.readString();
            workInfo.money = source.readString();
            workInfo.createTime = source.readString();
            workInfo.end_way = source.readString();
            workInfo.workContext = source.readString();
            workInfo.address = source.readString();
            workInfo.city = source.readString();
            workInfo.contacts = source.readString();
            workInfo.contactsWay = source.readString();
            workInfo.workStatu = source.readInt();
            return workInfo;
        }

        @Override
        public WorkInfo[] newArray(int size) {
            return new WorkInfo[size];
        }
    };
}