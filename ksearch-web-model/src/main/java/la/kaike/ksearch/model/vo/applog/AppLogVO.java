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
    private String userToken;

    /**
     * 日志内容
     */
    @Validate(maxLength = 1024)
    private String contentData;

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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }
}
