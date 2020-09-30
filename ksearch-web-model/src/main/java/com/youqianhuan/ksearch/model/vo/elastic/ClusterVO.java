/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.elastic;

import java.util.List;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月17日 下午4:37 $
 */
public class ClusterVO {

    private String name;

    private String status;

    private List<NodeStatsVO> nodes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NodeStatsVO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeStatsVO> nodes) {
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
