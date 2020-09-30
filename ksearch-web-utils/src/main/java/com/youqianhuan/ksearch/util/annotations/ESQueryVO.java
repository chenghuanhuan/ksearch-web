/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.util.annotations;

import java.lang.annotation.*;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2018年01月29日 上午10:51 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface ESQueryVO {
    /**
     * 索引正则表达式
     * @return
     */
    String indexRegular();

    /**
     * 类型
     * @return
     */
    String type();
}
