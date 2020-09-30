/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.query;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.model.vo.PageVO;

import java.util.List;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月28日 上午9:49 $
 */
public class SimpleQueryReqVO extends PageVO {

    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 索引
     */
    private List<String> indices;

    /**
     * 类型
     */
    private List<String> types;

    /**
     * 排序字段
     */
    private List<SortFieldVO> sorts;

    private String sort;

    private String order;

    private String source;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<SortFieldVO> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortFieldVO> sorts) {
        this.sorts = sorts;
    }

    public List<String> getIndices() {
        return indices;
    }

    public void setIndices(List<String> indices) {
        this.indices = indices;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
