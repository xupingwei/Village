package ink.alf.village.bean;

/**
 * @author 13793
 * 活动
 */
public class ActivitiBean {
    private String headUrl;
    private String title;
    private String pushName;
    private long pushTime;
    /**
     * 以","分割
     */
    private String catorogy;
    private String content;
    /**
     * 以","分割
     */
    private String imgUrls;
    private String address;
    private int follow;
    private int collect;


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

    public String getCatorogy() {
        return catorogy;
    }

    public void setCatorogy(String catorogy) {
        this.catorogy = catorogy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
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
                "pushName='" + pushName + '\'' +
                '}';
    }
}
