/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.util.constant;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月03日 下午2:55 $
 */
public enum  MethodNameEnum {
    PUT("PUT"),
    PATCH("PATCH"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    HEAD("HEAD");

    private String value = null;

    MethodNameEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
