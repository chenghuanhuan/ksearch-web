/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.es;

import com.youqianhuan.ksearch.biz.support.CustomPropertyPlaceholderConfigurer;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenghuanhuan@youqianhuan.com
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
                        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                        CustomPropertyPlaceholderConfigurer configurer = (CustomPropertyPlaceholderConfigurer) wac.getBean("propertyConfigurer");
                        String clusterHosts = configurer.getProperty(clusterName + ".hosts");
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
                    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                    CustomPropertyPlaceholderConfigurer configurer = (CustomPropertyPlaceholderConfigurer) wac.getBean("propertyConfigurer");
                    String host = configurer.getProperty(clusterName + ".http.hosts");
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
            this._client = new PreBuiltTransportClient(getSettings(clusterName));;
            //this._client = new PreBuiltXPackTransportClient(getSettings(clusterName));
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

}
