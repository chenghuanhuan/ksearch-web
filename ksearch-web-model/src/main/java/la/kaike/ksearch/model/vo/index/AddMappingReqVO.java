/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.index;

import la.kaike.ksearch.model.ClusterRequest;
import la.kaike.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月24日 下午3:08 $
 */
public class AddMappingReqVO extends ClusterRequest {

    @Validate(required = true,isNotBlank = true,maxLength = 128,regexp = "^[0-9A-Za-z]")
    private String index;

    @Validate(required = true,isNotBlank = true)
    private String type;

    @Validate(required = true,isNotBlank = true)
    private Boolean include_in_all;

    /**
     * mapping的json配置字符串
     */
    @Validate(required = true,isNotBlank = true)
    private String mappingsJson;

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

    public String getMappingsJson() {
        return mappingsJson;
    }

    public void setMappingsJson(String mappingsJson) {
        this.mappingsJson = mappingsJson;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
