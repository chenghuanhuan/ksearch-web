/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.es;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MetaData;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月01日 下午6:12 $
 */
public class ElasticClientUtil {

    private ElasticClientUtil(){
        throw new UnsupportedOperationException();
    }

    /**
     * 获取健康状况信息
     * @return
     */
    public static ClusterHealthResponse getClusterHealth(TransportClient transportClient){
        ClusterHealthResponse response = transportClient.admin().cluster().prepareHealth().execute().actionGet();
        System.out.println(response);
        return response;
    }

    /**
     * 获取节点状态信息
     * @return
     */
    public static NodesStatsResponse getNodeStats(TransportClient transportClient){
        NodesStatsResponse response = transportClient.admin().cluster().prepareNodesStats().execute().actionGet();
        return response;
    }

    public static MetaData getMetadata(TransportClient transportClient){
        //IndicesStatsResponse statsResponse = transportClient.admin().indices().prepareStats().execute().actionGet();
        //GetSettingsResponse response = transportClient.admin().indices().prepareGetSettings().execute().actionGet();
        // System.out.println(response.getSetting("product",""));
        //"index.number_of_shards" -> "5"
        //"index.number_of_replicas" -> "1"
        //"index.creation_date" -> "1498813262911"
        //"index.provided_name" -> "website"
        //"index.uuid" -> "6BUJ_EefQXa6VYRrIQKBGQ"
        //"index.version.created" -> "5040399"
        ClusterStateResponse clusterStateResponse = getClusterAdminClient(transportClient).prepareState().get();
        MetaData metaData = clusterStateResponse.getState().getMetaData();
        return metaData;
    }

    public static ClusterAdminClient getClusterAdminClient(TransportClient transportClient){
        return transportClient.admin().cluster();
    }

    /**
     * 获取状态信息
     * @return
     */
    public static IndicesStatsResponse getStats(TransportClient transportClient){
        IndicesStatsResponse response = transportClient.admin().indices().prepareStats().execute().actionGet();
        return response;
    }

    /**
     * 集群状态信息
     * @return
     */
    public static ClusterStateResponse getClusterState(TransportClient transportClient){
        ClusterStateResponse clusterStateResponse = transportClient.admin().cluster().prepareState().execute().actionGet();
        return clusterStateResponse;
    }

    public static IndicesAdminClient getIndicesAdminClient(TransportClient transportClient){
        return transportClient.admin().indices();
    }
}
