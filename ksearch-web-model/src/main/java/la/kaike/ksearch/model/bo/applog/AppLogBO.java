/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.bo.applog;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月29日 上午10:31 $
 */
public class AppLogBO {
    /**
     * 版本号
     */
    private String version;

    /**
     * 平台(1:iOS、2:android)
     */
    private Integer platform;

    /**
     * 手机操作系统版本
     */
    private String osVersion;

    /**
     * 唯一标识符(包名)
     */
    private String bundleIdentifier;

    /**
     * 用户id
     */
    private String clientToken;

    /**
     * 日志内容
     */
    private String contentData;

    /**
     * 手机品牌
     */
    private String brand;

    /**
     * 手机屏幕尺寸
     */
    private String resolution;
    /**
     * app安装来源
     */
    private String channel;

    /**
     * 手机型号
     */
    private String deviceModel;

    /**
     * 国际手机唯一标示码
     */
    private String imei;

    /**
     * 日志上报时间
     */
    private String uploadDate;

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBundleIdentifier() {
        return bundleIdentifier;
    }

    public void setBundleIdentifier(String bundleIdentifier) {
        this.bundleIdentifier = bundleIdentifier;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }
}
