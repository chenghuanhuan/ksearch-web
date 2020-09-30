/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.model.ClusterRequest;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月25日 上午11:13 $
 */
public class GetMappingReqVO extends ClusterRequest {
    @Validate(required = true)
    private String clusterName;
    @Validate(required = true,isNotBlank = true,desc = "索引名称")
    private String index;

    private String type;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

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
}
