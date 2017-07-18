/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.bo.ClusterHealthBO;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;

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
}
