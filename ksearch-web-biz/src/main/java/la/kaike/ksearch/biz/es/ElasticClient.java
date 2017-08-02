/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.es;

import com.baidu.disconf.client.usertools.IKuKoConfDataGetter;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:33 $
 */
public class ElasticClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticClient.class);

    private static Map<String,TransportClient> clientMap = new ConcurrentHashMap<>();

    public static TransportClient getClient(String clusterName){
        TransportClient client = clientMap.get(clusterName);
        if (client == null){
            String clusterHosts = IKuKoConfDataGetter.getStringValue(clusterName+".hosts");
            client = Builder.builder().setClusterName(clusterName).addNode(clusterHosts)._client;
            clientMap.put(clusterName,client);
            logger.info("Elastic client created!");
        }

        return client;
    }


    private static class Builder {
        private TransportClient _client;

        public static Builder builder(){
            return new Builder();
        }
        public ElasticClient.Builder setClusterName(String clusterName) {
            this._client = new PreBuiltTransportClient(getSettings(clusterName));;
            return this;
        }

        private Settings getSettings(String clusterName) {
            Settings settings = Settings
                    .builder()
                    .put("cluster.name",clusterName)
                    .put("client.transport.ignore_cluster_name", false)
                    //这个客户端可以嗅到集群的其它部分，并将它们加入到机器列表。为了开启该功能，设置client.transport.sniff为true。
                    .put("client.transport.sniff",true)
                    .build();
            return settings;
        }

        public ElasticClient.Builder addNode(String nodeAddrs) {
            String[] hosts = nodeAddrs.split(",");
            for (String host : hosts) {
                String infos[] = host.split(":");
                addNode(infos[0], infos.length == 2 ? Integer.valueOf(infos[1]) : 9300);
            }
            return this;
        }

        public ElasticClient.Builder addNode(String ip, int port) {
            try {
                this._client.addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(ip), port));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return this;
        }

        public TransportClient build() {
            return this._client;
        }
    }

    public static void main(String[] args) {


       /* System.out.println(SortOrder.valueOf("DESC"));
        TransportClient client = ElasticClient.newInstance().getTransportClient();

        SearchRequestBuilder builder = null;

        // 索引

        builder = client.prepareSearch("test");
        builder.setTypes("ce");//.addStoredField("a").addStoredField("b");
        builder.addStoredField("_source");
        builder.addStoredField("doc_date");
        //builder.addStoredField("a");
        // 前缀匹配
        //QueryBuilder queryBuilder = QueryBuilders.prefixQuery("a","aa");

       *//* QueryBuilder queryBuilder = QueryBuilders.termQuery("a","aa");
        builder.setQuery(queryBuilder);*//*
        *//*SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
        builder.setSource(sourceBuilder);*//*
        //builder.addSort("e", SortOrder.DESC);

       builder.setFrom(0).setSize(3);
        SearchResponse searchResponse = builder.get();
        System.out.println(searchResponse);
*/


        for (int i=0;i<100;i++){
            String json = "{" +
                    "  \"abcdef f\":\"aa3"+i+"\"," +
                    "  \"g\":\"g"+i+"\"," +
                    "  \"test1\":\"abcdefg "+i+"\"" +
                    "}";
            ElasticClient.getClient("my-application")
                    .prepareIndex("test2","test1",""+i)
                    .setSource(json, XContentType.JSON).get();
        }

        System.out.println(ElasticClient.getClient("my-application"));

        GetMappingsRequest request = new GetMappingsRequest();
        request.indices("test2");
        request.types("test");
        GetMappingsResponse response = ElasticClient.getClient("my-application").admin().indices()
                .getMappings(request).actionGet();
        ImmutableOpenMap<String,ImmutableOpenMap<String,MappingMetaData>> metaDataImmutableOpenMap = response.getMappings();
        Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String,MappingMetaData>>> iterator = metaDataImmutableOpenMap.iterator();


    }

}
