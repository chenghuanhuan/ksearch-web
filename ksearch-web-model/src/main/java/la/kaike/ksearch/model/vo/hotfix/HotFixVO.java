/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.hotfix;

import la.kaike.ksearch.model.validate.Validate;
import la.kaike.ksearch.model.vo.PageVO;
import la.kaike.ksearch.util.annotations.ESQuery;
import la.kaike.ksearch.util.annotations.ESQueryVO;
import la.kaike.ksearchclient.annotations.FieldType;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月12日 下午5:37 $
 */
@ESQueryVO(index = "hotfix",type = "log")
public class HotFixVO extends PageVO {

    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    /**
     * 用户ID
     */
    @ESQuery(type = FieldType.keyword)
    private String userId;

    /**
     * 设备ID
     */
    @ESQuery(type = FieldType.keyword,order = 1)
    private String deviceId;
    /**
     * app版本
     */
    @ESQuery(type = FieldType.Integer,order = 2)
    private Integer appVersion;
    /**
     * 热修复版本
     */
    @ESQuery(type = FieldType.keyword,order = 3)
    private String patchId;
    /**
     * 系统版本
     */
    @ESQuery(type = FieldType.Integer,order = 4)
    private Integer systemVersion;
    /**
     * 品牌
     */
    @ESQuery(type = FieldType.keyword,order = 5)
    private String brand;

    /**
     * 型号
     */
    @ESQuery(type = FieldType.keyword,order = 6)
    private String model;
    /**
     * 事件代码
     */
    @ESQuery(type = FieldType.keyword,order = 7)
    private String event;

    /**
     * 额外字段
     */
    @ESQuery(type = FieldType.keyword,order = 8)
    private String extra1;

    /**
     * 额外字段
     */
    @ESQuery(type = FieldType.keyword,order = 9)
    private String extra2;
    /**
     * 额外字段
     */
    @ESQuery(type = FieldType.keyword,order = 10)
    private String extra3;
    /**
     * 额外字段
     */
    @ESQuery(type = FieldType.keyword,order = 11)
    private String extra4;
    /**
     * 额外字段
     */
    @ESQuery(type = FieldType.keyword,order = 12)
    private String extra5;

    @ESQuery(type = FieldType.Date,order = -1,sortable = true)
    private String datetime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    public String getPatchId() {
        return patchId;
    }

    public void setPatchId(String patchId) {
        this.patchId = patchId;
    }

    public Integer getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(Integer systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
    }

    public String getExtra4() {
        return extra4;
    }

    public void setExtra4(String extra4) {
        this.extra4 = extra4;
    }

    public String getExtra5() {
        return extra5;
    }

    public void setExtra5(String extra5) {
        this.extra5 = extra5;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
