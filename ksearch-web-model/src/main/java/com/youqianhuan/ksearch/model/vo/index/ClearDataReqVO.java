/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import com.youqianhuan.ksearch.model.ClusterRequest;
import com.youqianhuan.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月25日 下午1:46 $
 */
public class ClearDataReqVO extends ClusterRequest {

    @Validate(required = true)
    private String clusterName;
    @Validate(required = true,isNotBlank = true,desc = "索引名称")
    private String index;

    @Validate
    private String type;


    @Override
    public String getClusterName() {
        return clusterName;
    }

    @Override
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
