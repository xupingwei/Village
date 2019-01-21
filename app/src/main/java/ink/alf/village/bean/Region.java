package ink.alf.village.bean;

import java.io.Serializable;

/**
 * @author 13793
 */
public class Region implements Serializable {


    /**
     * id : 2c9fbfb0686e3b0201686e3cec020000
     * createTime : 2019-01-21 10:28:33
     * updateTime : 2019-01-21 10:28:33
     * province : 陕西省
     * city : 西安市
     * district : 雁塔区
     * longitude : 108.95
     * latitude : 34.22
     */

    private String id;
    private String createTime;
    private String updateTime;
    private String province;
    private String city;
    private String district;
    private double longitude;
    private double latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
