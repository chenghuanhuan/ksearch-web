/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.user;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.BaseRequest;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午7:36 $
 */
public class UserDelReqVO extends BaseRequest {

    @Validate(required = true,isNotBlank = true)
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
