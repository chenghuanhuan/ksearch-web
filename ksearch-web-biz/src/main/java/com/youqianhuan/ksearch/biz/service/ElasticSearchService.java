/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.service;

import com.youqianhuan.ksearch.model.PageResponse;
import com.youqianhuan.ksearch.model.bo.ClusterHealthBO;
import com.youqianhuan.ksearch.model.vo.elastic.ClusterStatisticsVO;
import com.youqianhuan.ksearch.model.vo.elastic.IndicesVO;
import com.youqianhuan.ksearch.model.vo.index.*;
import com.youqianhuan.ksearch.model.vo.query.SimpleQueryReqVO;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:21 $
 */
public interface ElasticSearchService {

    /**
     * 获取集群健康情况
     * @return
     */
    ClusterHealthBO clusterHealth(String clusterName);


    /**
     * 获取节点信息
     */
    NodesStatsResponse nodeStats(String clusterName);

    /**
     * 获取统计信息
     * @param clusterName
     * @return
     */
    ClusterStatisticsVO clusterStatistics(String clusterName);

    /**
     * 获取索引列表
     * @param clusterName
     * @return
     */
    List<IndicesVO> getIndicesVO(String clusterName);

    /**
     * 添加索引
     * @param addIndexVO
     */
    void addIndex(AddIndexReqVO addIndexVO);

    /**
     * 删除索引
     * @param delIndexVO
     */
    void delIndex(DelIndexReqVO delIndexVO);

    /**
     * 刷新索引
     */
    void refreshIndex(RefreshIndexReqVO refreshIndexVO);

    /**
     * 关闭索引
     * @param closeIndexVO
     */
    void closeIndex(CloseIndexReqVO closeIndexVO);

    /**
     * 打开索引
     * @param openIndexReqVO
     */
    void openIndex(OpenIndexReqVO openIndexReqVO);

    /**
     * flush
     * @param flushIndexVO
     */
    void flushIndex(FlushIndexReqVO flushIndexVO);

    /**
     * 优化索引
     * @param optimizeIndexVO
     */
    void optimizeIndex(OptimizeIndexReqVO optimizeIndexVO);

    /**
     * 创建别名
     * @param aliasIndexVO
     */
    void createAlias(CreateAliasReqVO aliasIndexVO);

    /**
     * 删除别名
     * @param delAliasVO
     */
    void delAlias(DelAliasReqVO delAliasVO);

    /**
     * 添加mapping
     * @param addMappingVO
     */
    void addMapping(AddMappingReqVO addMappingVO);

    /**
     * 获取mapping
     * @param getMappingVO
     * @return
     */
    List<MappingVO> getAllMapping(GetMappingReqVO getMappingVO);

    /**
     * 查询
     * @param simpleQueryReqVO
     * @return
     */
    PageResponse simpleQuery(SimpleQueryReqVO simpleQueryReqVO) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    /**
     * 添加文档
     * @param addDocReqVO
     * @return
     */
    String addDoc(AddDocReqVO addDocReqVO);

    /**
     * 执行dsl语句
     * @param dsl
     * @return
     */
    String executeDSL(String method,String uri,String dsl,String clusterName) throws IOException;

    /**
     * 清空数据
     * @param clearDataReqVO
     */
    String clearData(ClearDataReqVO clearDataReqVO) throws IOException;

    /**
     * 清除较早的索引
     */
    void clearOldIndex(String clusterName) throws ParseException;
}
