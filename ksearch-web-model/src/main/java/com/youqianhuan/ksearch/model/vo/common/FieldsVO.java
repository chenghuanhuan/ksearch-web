/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.common;


import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月01日 上午10:07 $
 */
public class FieldsVO{
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String type;

    private List<FieldsVO> children;

    public List<FieldsVO> getChildren() {
        return children;
    }

    public void setChildren(List<FieldsVO> children) {
        this.children = children;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
