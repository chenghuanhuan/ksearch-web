/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.es;

import com.baidu.disconf.client.usertools.IKuKoConfDataGetter;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:33 $
 */
public class ElasticClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticClient.class);

    private static Map<String,TransportClient> clientMap = new ConcurrentHashMap<>();

    private static Map<String,JestClient> httpClientMap = new ConcurrentHashMap<>();

    public static TransportClient getClient(String clusterName){
        TransportClient client = clientMap.get(clusterName);

        if (client == null){
            synchronized (ElasticClient.class) {
                try {
                    if (clientMap.get(clusterName) == null) {
                        String clusterHosts = IKuKoConfDataGetter.getStringValue(clusterName + ".hosts");
                        //client = new PreBuiltXPackTransportClient(Builder.builder().setClusterName(clusterName).addNode(clusterHosts));
                        client = Builder.builder().setClusterName(clusterName).addNode(clusterHosts)._client;
                        ElasticClientUtil.getClusterHealth(client);
                        clientMap.put(clusterName, client);
                        logger.info("Elastic client created!");
                    }
                }catch (Exception e){
                    logger.error("create elastic client fail !",e);
                }
            }

        }

        logger.info("====================================get client================================");
        logger.info(""+client);
        return clientMap.get(clusterName);
    }

    public static JestClient getHttpClient(String clusterName){
        JestClient client= httpClientMap.get(clusterName);
        if (client==null) {
            synchronized (ElasticClient.class) {
                if (httpClientMap.get(clusterName)==null) {
                    JestClientFactory factory = new JestClientFactory();
                    List<String> urls = new ArrayList<>();
                    String host = IKuKoConfDataGetter.getStringValue(clusterName + ".http.hosts");
                    String infos[] = host.split(":");
                    if (infos.length>1){
                        urls.add("http://"+host);
                    }
                    urls.add("http://"+host+":9200");
                    factory.setHttpClientConfig(new HttpClientConfig
                            .Builder(urls)
                            .multiThreaded(true)
                            //Per default this implementation will create no more than 2 concurrent connections per given route
                            .defaultMaxTotalConnectionPerRoute(2)
                            // and no more 20 connections in total
                            .maxTotalConnection(10)
                            .build());
                    client = factory.getObject();
                    httpClientMap.put(clusterName, client);
                }
            }
        }
        return client;
    }


    private static class Builder {
        private TransportClient _client;

        public static Builder builder(){
            return new Builder();
        }
        public ElasticClient.Builder setClusterName(String clusterName) {
            //this._client = new PreBuiltTransportClient(getSettings(clusterName));;
            this._client = new PreBuiltXPackTransportClient(getSettings(clusterName));
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

        SearchResponse response =  ElasticClient.getClient("my-application").prepareSearch("test2").setTypes("test")
                .setQuery(QueryBuilders.wrapperQuery("")).get();

    }

}
