/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.ewtlog;

import la.kaike.ksearch.model.validate.Validate;
import la.kaike.ksearch.model.vo.PageVO;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月02日 下午2:00 $
 */
public class EwtLogVO extends PageVO {

    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    @Validate(maxLength = 32)
    private String startTime;

    @Validate(maxLength = 32)
    private String endTime;

    private String datetime;

    private String hostname;

    private String appname;

    private String method;

    private String remoteIp;

    private String source;

    private String type;

    private String url;

    private Long userId;

    private String params;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "EwtLogVO{" +
                "clusterName='" + clusterName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", datetime='" + datetime + '\'' +
                ", hostname='" + hostname + '\'' +
                ", appname='" + appname + '\'' +
                ", method='" + method + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", userId=" + userId +
                ", params='" + params + '\'' +
                ", offset='" + this.getOffset() + '\'' +
                ", limit='" + this.getLimit() + '\'' +
                '}';
    }
}
