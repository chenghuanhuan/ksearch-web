/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.vo.index;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月25日 上午11:19 $
 */
public class MappingVO {


    private String index;

    private String type;


    private Boolean include_in_all;

    private String dynamic;

    private List<PropertiesVO> properties;

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Boolean getInclude_in_all() {
        return include_in_all;
    }

    public void setInclude_in_all(Boolean include_in_all) {
        this.include_in_all = include_in_all;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropertiesVO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesVO> properties) {
        this.properties = properties;
    }
}
