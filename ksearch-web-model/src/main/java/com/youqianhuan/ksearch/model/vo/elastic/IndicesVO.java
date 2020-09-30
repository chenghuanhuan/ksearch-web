/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.elastic;

import java.util.List;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月18日 下午3:02 $
 */
public class IndicesVO {

    private String index;
    private Long docs;
    private String primarySize;
    private Integer shards;
    private Integer replicas;
    private String status;

    private List<String> aliases;

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Long getDocs() {
        return docs;
    }

    public void setDocs(Long docs) {
        this.docs = docs;
    }

    public String getPrimarySize() {
        return primarySize;
    }

    public void setPrimarySize(String primarySize) {
        this.primarySize = primarySize;
    }

    public Integer getShards() {
        return shards;
    }

    public void setShards(Integer shards) {
        this.shards = shards;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
