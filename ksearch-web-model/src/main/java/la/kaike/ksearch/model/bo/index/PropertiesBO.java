/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.bo.index;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月25日 上午10:26 $
 */
public class PropertiesBO {
    /** 类型 */
    private String type;

    /**
     * 控制属性如何被索引，可选值 analyzed、not_analyzed(字符串类型)、no
     * "analyzed"//分词，不分词是：not_analyzed ，设置成no，字段将不会被索引
     */
    private Boolean index;

    /**
     * true,false，是否单独设置此字段的是否存储而从_source字段中分离，默认是false，只能搜索，不能获取值
     */
    private Boolean store;

    /**
     * 默认为1，定义了文档中该字段的重要性，越高越重要
     */
    private Integer boost;

    /**
     * 如果一个字段为null值(空数组或者数组都是null值)的话不会被索引及搜索到，null_value参数可以显示替代null values为指定值，这样使得字段可以被搜索到。
     * 设置一些缺失字段的初始化值，只有string可以使用，分词字段的null值也会被分词
     */
    private String null_value;

    /**
     * 指定该字段是否应该包括在_all字段里头，默认情况下都会包含。
     */
    private Boolean include_in_all;

    /**
     * 超过XX个字符的文本，将会被忽略，不被索引
     */
    private Integer ignore_above;

    /**
     * 指定分词器
     */
    private String analyzer;

    /**
     * 设置搜索时的分词器，默认跟ananlyzer是一致的，比如index时用standard+ngram，搜索时用standard用来完成自动提示功能
     */
    private String search_analyzer;

    private JSONObject properties;

    public JSONObject getProperties() {
        return properties;
    }

    public void setProperties(JSONObject properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIndex() {
        return index;
    }

    public void setIndex(Boolean index) {
        this.index = index;
    }

    public Boolean getStore() {
        return store;
    }

    public void setStore(Boolean store) {
        this.store = store;
    }

    public Integer getBoost() {
        return boost;
    }

    public void setBoost(Integer boost) {
        this.boost = boost;
    }

    public String getNull_value() {
        return null_value;
    }

    public void setNull_value(String null_value) {
        this.null_value = null_value;
    }

    public Boolean getInclude_in_all() {
        return include_in_all;
    }

    public void setInclude_in_all(Boolean include_in_all) {
        this.include_in_all = include_in_all;
    }

    public Integer getIgnore_above() {
        return ignore_above;
    }

    public void setIgnore_above(Integer ignore_above) {
        this.ignore_above = ignore_above;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public String getSearch_analyzer() {
        return search_analyzer;
    }

    public void setSearch_analyzer(String search_analyzer) {
        this.search_analyzer = search_analyzer;
    }
}
