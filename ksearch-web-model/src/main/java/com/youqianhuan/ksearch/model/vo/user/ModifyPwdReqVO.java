/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.user;

import com.youqianhuan.ksearch.model.validate.Validate;
import com.youqianhuan.ksearch.BaseRequest;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月08日 下午2:44 $
 */
public class ModifyPwdReqVO extends BaseRequest {

    @Validate(required = true,isNotBlank = true)
    private String userId;

    @Validate(required = true,isNotBlank = true)
    private String password;
    @Validate(required = true,isNotBlank = true)
    private String currPwd;
    @Validate(required = true,isNotBlank = true)
    private String secondPwd;

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

    public String getCurrPwd() {
        return currPwd;
    }

    public void setCurrPwd(String currPwd) {
        this.currPwd = currPwd;
    }

    public String getSecondPwd() {
        return secondPwd;
    }

    public void setSecondPwd(String secondPwd) {
        this.secondPwd = secondPwd;
    }
}
