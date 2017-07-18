/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import com.alibaba.fastjson.JSON;
import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.model.bo.ClusterHealthBO;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:21 $
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Override
    public ClusterHealthBO clusterHealth(String clusterName) {
        ClusterHealthResponse obj = ElasticClient.newInstance().getClusterHealth();
        ClusterHealthBO clusterHealthBO = new ClusterHealthBO();
        if(obj!=null){
            clusterHealthBO = JSON.parseObject(obj.toString(),ClusterHealthBO.class);
        }

        return clusterHealthBO;
    }

    @Override
    public NodesStatsResponse nodeStats(String clusterName) {
        NodesStatsResponse response = ElasticClient.newInstance().getNodeState();
        return response;
    }
}
