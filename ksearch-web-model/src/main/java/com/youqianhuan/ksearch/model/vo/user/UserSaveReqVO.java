/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.user;

import com.youqianhuan.ksearch.BaseRequest;
import com.youqianhuan.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午7:33 $
 */
public class UserSaveReqVO extends BaseRequest {

    /**
     * 主键id
     */
    private String userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 名字
     */
    @Validate(required = true,isNotBlank = true)
    private String username;

    /**
     * 角色id
     */
    @Validate(required = true,isNotBlank = true)
    private Integer roleId;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
