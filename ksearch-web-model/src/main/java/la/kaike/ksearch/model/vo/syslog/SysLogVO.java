/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.syslog;

import la.kaike.ksearch.model.validate.Validate;
import la.kaike.ksearch.model.vo.PageVO;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年11月16日 下午5:37 $
 */
public class SysLogVO extends PageVO {
    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    private String index;

    private String type;


    private String message;

    private String className;

    private String level;
    private String host;
    private String remoteAddr;
    private String requestURIWithQueryString;
    private String userId;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestURIWithQueryString() {
        return requestURIWithQueryString;
    }

    public void setRequestURIWithQueryString(String requestURIWithQueryString) {
        this.requestURIWithQueryString = requestURIWithQueryString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
