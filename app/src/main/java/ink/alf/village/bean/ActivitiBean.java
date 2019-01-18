package ink.alf.village.bean;

import java.io.Serializable;

/**
 * @author 13793
 * 活动
 * {
 *         "content": "图片图",
 *         "follow": 0,
 *         "collect": 0,
 *         "catagory": "问事",
 *         "contentImages": "http://images.xaolaf.com/bbd697a19a5fd28fca46971a3105ab36",
 *         "salt": "THING",
 *         "headUrl": "http://images.xaolaf.com/7d2fd7d75bb9913f76cba43967e4d9fc",
 *         "createTime": "2019-01-18T08:09:48.000+0000",
 *         "address": "北京市海淀区中关村大街",
 *         "pushNickName": "Jone",
 *         "pushUserId": "40289ecc67cfcfb10167cfd8ca370000"
 *       }
 */
public class ActivitiBean implements Serializable {
    private String headUrl;
    private String pushNickName;
    private long createTime;
    private String address;

    /**
     * 以","分割
     */
    private String images = "";
    private String content;
    private int follow;
    private int collect;
    private String pushUserId;

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

    public String getPushNickName() {
        return pushNickName;
    }

    public void setPushNickName(String pushNickName) {
        this.pushNickName = pushNickName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPushUserId() {
        return pushUserId;
    }

    public void setPushUserId(String pushUserId) {
        this.pushUserId = pushUserId;
    }
}
