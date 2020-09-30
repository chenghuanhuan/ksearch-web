/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.query;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.model.ClusterRequest;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月03日 上午10:36 $
 */
public class ExecuteReqVO extends ClusterRequest{

    @Validate(isNotBlank = true)
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
