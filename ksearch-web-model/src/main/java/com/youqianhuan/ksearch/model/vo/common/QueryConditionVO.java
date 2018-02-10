/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.common;

import com.youqianhuan.ksearch.BaseRequest;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月13日 上午11:41 $
 */
public class QueryConditionVO extends BaseRequest {

    /**
     * class名
     */
    private String conditionKey;

    /**
     * 索引名称
     */
    private String index;

    /**
     * 类型名称
     */
    private String type;

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

    public String getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }
}
