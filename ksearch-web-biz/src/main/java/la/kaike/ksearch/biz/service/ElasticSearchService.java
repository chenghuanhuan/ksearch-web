/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.bo.ClusterHealthBO;
import la.kaike.ksearch.model.vo.elastic.ClusterStatisticsVO;
import la.kaike.ksearch.model.vo.elastic.IndicesVO;
import la.kaike.ksearch.model.vo.index.AddIndexVO;
import la.kaike.ksearch.model.vo.index.DelIndexVO;
import la.kaike.ksearch.model.vo.index.RefreshIndexVO;
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
    void addIndex(AddIndexVO addIndexVO);

    /**
     * 删除索引
     * @param delIndexVO
     */
    void delIndex(DelIndexVO delIndexVO);

    /**
     * 刷新索引
     */
    void refreshIndex(RefreshIndexVO refreshIndexVO);
}
