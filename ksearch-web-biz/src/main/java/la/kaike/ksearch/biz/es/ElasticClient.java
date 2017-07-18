/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.es;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:33 $
 */
public class ElasticClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticClient.class);
    private volatile static ElasticClient client;

    private TransportClient transportClient;

    public static ElasticClient newInstance(){
        if (client == null){
            synchronized (ElasticClient.class){
                if (client == null){
                    try {
                        Settings settings = Settings
                                .builder()
                                .put("cluster.name","my-application")
                                //这个客户端可以嗅到集群的其它部分，并将它们加入到机器列表。为了开启该功能，设置client.transport.sniff为true。
                                .put("client.transport.sniff",true)
                                .build();


                        TransportClient transportClient = new PreBuiltTransportClient(settings)
                                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.70.76"),9300));
                        client = new ElasticClient();
                        client.setTransportClient(transportClient);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return client;
    }

    public void setTransportClient(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    public void close(){
        transportClient.close();
    }

    /**
     * 获取健康状况信息
     * @return
     */
    public  ClusterHealthResponse getClusterHealth(){
        ClusterHealthResponse response = transportClient.admin().cluster().prepareHealth().execute().actionGet();
        System.out.println(response);
        return response;
    }

    /**
     * 获取节点状态信息
     * @return
     */
    public  NodesStatsResponse getNodeStats(){
        NodesStatsResponse response = getClusterAdminClient().prepareNodesStats().execute().actionGet();
        return response;
    }

    public MetaData getMetadata(){
        //IndicesStatsResponse statsResponse = transportClient.admin().indices().prepareStats().execute().actionGet();
        //GetSettingsResponse response = transportClient.admin().indices().prepareGetSettings().execute().actionGet();
       // System.out.println(response.getSetting("product",""));
        //"index.number_of_shards" -> "5"
        //"index.number_of_replicas" -> "1"
        //"index.creation_date" -> "1498813262911"
        //"index.provided_name" -> "website"
        //"index.uuid" -> "6BUJ_EefQXa6VYRrIQKBGQ"
        //"index.version.created" -> "5040399"
        ClusterStateResponse clusterStateResponse = getClusterAdminClient().prepareState().get();
        MetaData metaData = clusterStateResponse.getState().getMetaData();
        return metaData;
    }

    private ClusterAdminClient getClusterAdminClient(){
        return transportClient.admin().cluster();
    }

    /**
     * 获取状态信息
     * @return
     */
    public IndicesStatsResponse getStats(){
       IndicesStatsResponse response = transportClient.admin().indices().prepareStats().execute().actionGet();
       return response;
    }

    public static void main(String[] args) {
        IndicesStatsResponse response = ElasticClient.newInstance().getStats();
        System.out.println(ElasticClient.newInstance().getMetadata());
    }

}
