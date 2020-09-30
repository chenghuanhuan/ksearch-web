/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import com.youqianhuan.ksearch.model.ClusterRequest;
import com.youqianhuan.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月20日 下午2:18 $
 */
public class AddIndexReqVO extends ClusterRequest {

    @Validate(required = true)
    private String clusterName;
    @Validate(required = true,isNotBlank = true,maxLength = 128,regexp = "^(?!_)[a-zA-Z0-9_.-]+")
    private String index;
    @Validate(required = true,minValue = "1")
    private Integer numberOfShards;
    @Validate(required = true,minValue = "1")
    private Integer numberOfReplicas;

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

    public Integer getNumberOfShards() {
        return numberOfShards;
    }

    public void setNumberOfShards(Integer numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public Integer getNumberOfReplicas() {
        return numberOfReplicas;
    }

    public void setNumberOfReplicas(Integer numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }
}
