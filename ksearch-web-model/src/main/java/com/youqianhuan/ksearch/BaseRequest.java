/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch;

import java.io.Serializable;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月02日 上午11:21 $
 */
public class BaseRequest implements Serializable {

    /**
     * 是否分词
     */
    private boolean analyzer = true;


    public boolean isAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(boolean analyzer) {
        this.analyzer = analyzer;
    }
}
