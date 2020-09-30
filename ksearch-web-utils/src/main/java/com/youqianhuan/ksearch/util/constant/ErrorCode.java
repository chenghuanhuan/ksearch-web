/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.util.constant;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月09日 上午10:06 $
 */
public enum  ErrorCode {

    NO_LOGIN(1000,"用户未登录");

    private int code = 0;
    private String msg;

    ErrorCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public  int getCode(){
        return code;
    }
}
