/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.model.ClusterRequest;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月21日 上午11:33 $
 */
public class CreateAliasReqVO extends ClusterRequest {
    @Validate(required = true)
    private String clusterName;
    @Validate(required = true,isNotBlank = true,desc = "索引名称")
    private String index;

    /**
     * 别名
     */
    @Validate(isNotBlank = true,regexp = "^(?!_)[a-zA-Z0-9_.-]+")
    private List<String> aliases;

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

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
}
