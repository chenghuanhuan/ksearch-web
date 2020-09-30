/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.login;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.BaseRequest;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午2:09 $
 */
public class LoginReqVO extends BaseRequest {
    /**
     * 用户ID
     */
    @Validate(maxLength = 32,required = true,isNotBlank = true)
    private String userId;
    /**
     * 密码
     */
    @Validate(maxLength = 32,required = true,isNotBlank = true)
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
