package ink.alf.village.bean;

import java.io.Serializable;

/**
 * @author 13793
 * 活动
 */
public class ActivitiBean implements Serializable {
    private String headUrl;
    private String title;
    private String pushName;
    private long pushTime;
    private String address;

    /**
     * 以","分割
     */
    private String images = "";
    private String content;
    private int follow;
    private int collect;
    private String userId;

    /**
     * 以","分割,"找车，找人，问事"
     */
    private String catagory;

    /**
     * 标签，以逗号隔开如：“VEHICLE，PERSON，THING”
     */
    private String salt;

    public ActivitiBean() {
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushName() {
        return pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName;
    }

    public long getPushTime() {
        return pushTime;
    }

    public void setPushTime(long pushTime) {
        this.pushTime = pushTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }


    @Override
    public String toString() {
        return "ActivitiBean{" +
                "headUrl='" + headUrl + '\'' +
                ", title='" + title + '\'' +
                ", pushName='" + pushName + '\'' +
                ", pushTime=" + pushTime +
                ", address='" + address + '\'' +
                ", images='" + images + '\'' +
                ", content='" + content + '\'' +
                ", follow=" + follow +
                ", collect=" + collect +
                ", userId='" + userId + '\'' +
                ", catagory='" + catagory + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
