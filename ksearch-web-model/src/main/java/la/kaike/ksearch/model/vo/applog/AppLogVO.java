/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.applog;

import la.kaike.ksearch.model.validate.Validate;
import la.kaike.ksearch.model.vo.PageVO;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月28日 下午3:54 $
 */
public class AppLogVO extends PageVO {

    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    /**
     * 版本号
     */
    @Validate(maxLength = 1024)
    private String version;

    /**
     * 平台(1:iOS、2:android)
     */
    @Validate(enumValues = "1,2")
    private Integer platform;

    /**
     * 手机操作系统版本
     */
    @Validate(maxLength = 1024)
    private String osVersion;

    /**
     * 唯一标识符(包名)
     */
    @Validate(maxLength = 1024)
    private String bundleIdentifier;

    /**
     * 用户id
     */
    @Validate(maxLength = 1024)
    private String clientToken;

    /**
     * 日志内容
     */
    @Validate(maxLength = 1024)
    private String contentData;

    @Validate(maxLength = 128)
    private String uploadDate;

    /**
     * 手机品牌
     */
    @Validate(maxLength = 128)
    private String brand;

    /**
     * 手机屏幕尺寸
     */
    @Validate(maxLength = 128)
    private String resolution;
    /**
     * app安装来源
     */
    @Validate(maxLength = 128)
    private String channel;

    /**
     * 手机型号
     */
    @Validate(maxLength = 128)
    private String deviceModel;

    /**
     * 国际手机唯一标示码
     */
    @Validate(maxLength = 256)
    private String imei;



    private String startTime;

    private String endTime;


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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
