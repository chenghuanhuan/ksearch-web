/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.index;

/**
 *  mapping的配置
 *
 *  可以修改的项：
    增加新的类型定义
    增加新的字段
    增加新的分析器

    不允许修改的项：
    更改字段类型(比如文本改为数字)
    更改存储为不存储，反之亦然
    更改索引属性的值
    更改已索引文档的分析器
    注意的是新增字段或更改分析器之后，需要再次对所有文档进行索引重建
 *
 *
 *
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月24日 下午3:13 $
 */
public class PropertiesVO {

    /** 类型 */
    private String type;

    private String format;

    /**
     * 控制属性如何被索引，可选值 analyzed、not_analyzed(字符串类型)、no
     * "analyzed"//分词，不分词是：not_analyzed ，设置成no，字段将不会被索引
     */
    private String index;

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

}
