/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.BaseRequest;

/**
 * 入参父类
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月14日 上午11:08 $
 */
public class ClusterRequest extends BaseRequest{

    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
