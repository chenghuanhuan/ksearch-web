/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import com.youqianhuan.ksearch.model.ClusterRequest;
import com.youqianhuan.ksearch.model.validate.Validate;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月20日 下午5:29 $
 */
public class CloseIndexReqVO extends ClusterRequest {
    @Validate(required = true)
    private String clusterName;

    @Validate(isNotBlank = true)
    private List<String> indices;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public List<String> getIndices() {
        return indices;
    }

    public void setIndices(List<String> indices) {
        this.indices = indices;
    }
}
