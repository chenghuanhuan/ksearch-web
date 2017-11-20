/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.index;

import la.kaike.ksearch.model.ClusterRequest;
import la.kaike.ksearch.model.validate.Validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月24日 下午3:08 $
 */
public class AddMappingReqVO extends ClusterRequest {

    /**
     * 索引：数字、字母下划线、不能以下划线开头
     */
    @Validate(required = true,isNotBlank = true,maxLength = 128,regexp = "^(?!_)[a-zA-Z0-9_.-]+")
    private String index;

    @Validate(required = true,isNotBlank = true)
    private String type;

    @Validate(required = true,isNotBlank = true)
    private Boolean include_in_all;

    /**
     * 动态映射规则
     */
    private String dynamic;
    /**
     * mapping的json配置字符串
     */
    @Validate(required = true,isNotBlank = true)
    private String mappingsJson;


    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
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

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^(?!_)[a-zA-Z0-9_.-]+");
        Matcher matcher = pattern.matcher("ksearch-api.ksearch-api-biz-service.2017-11-20");
        System.out.println(matcher.matches());
    }
}
