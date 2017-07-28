/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.bo.ClusterHealthBO;
import la.kaike.ksearch.model.vo.elastic.ClusterStatisticsVO;
import la.kaike.ksearch.model.vo.elastic.IndicesVO;
import la.kaike.ksearch.model.vo.index.*;
import la.kaike.ksearch.model.vo.query.SimpleQueryReqVO;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;

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
    String simpleQuery(SimpleQueryReqVO simpleQueryReqVO);
}
