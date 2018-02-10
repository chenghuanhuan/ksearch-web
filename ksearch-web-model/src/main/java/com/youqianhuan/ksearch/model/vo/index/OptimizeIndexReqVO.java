/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import com.youqianhuan.ksearch.model.ClusterRequest;
import com.youqianhuan.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月21日 上午10:42 $
 */
public class OptimizeIndexReqVO extends ClusterRequest {

    @Validate(required = true)
    private String clusterName;

    @Validate(isNotBlank = true)
    private String index;

    /**
     * 最大索引段数
     */
    private Integer maxNumSegments;

    /**
     * 只删除被标记为删除的
     */
    private Boolean onlyExpungeDeletes;

    /**
     * 优化后刷新
     */
    private Boolean flush;

    /**
     * 等待合并
     */
    private Boolean waitForMerge;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getMaxNumSegments() {
        return maxNumSegments;
    }

    public void setMaxNumSegments(Integer maxNumSegments) {
        this.maxNumSegments = maxNumSegments;
    }

    public Boolean getOnlyExpungeDeletes() {
        return onlyExpungeDeletes;
    }

    public void setOnlyExpungeDeletes(Boolean onlyExpungeDeletes) {
        this.onlyExpungeDeletes = onlyExpungeDeletes;
    }

    public Boolean getFlush() {
        return flush;
    }

    public void setFlush(Boolean flush) {
        this.flush = flush;
    }

    public Boolean getWaitForMerge() {
        return waitForMerge;
    }

    public void setWaitForMerge(Boolean waitForMerge) {
        this.waitForMerge = waitForMerge;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

}
