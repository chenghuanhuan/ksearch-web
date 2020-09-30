/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.es;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.DocumentResult;

import java.io.IOException;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月03日 上午10:39 $
 */
public class ElasticClientHttpClient {

    public static String execute() throws IOException {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://172.16.70.76:9200")
                .multiThreaded(true)
                //Per default this implementation will create no more than 2 concurrent connections per given route
                .defaultMaxTotalConnectionPerRoute(2)
                // and no more 20 connections in total
                .maxTotalConnection(10)
                        .build());
        JestClient client = factory.getObject();
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"term\": {\n" +
                "      \"test1\": \"abcdefg\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //Search search = new Search.Builder(query).addIndex("test2").addType("test1").build();
        SearchExt searchExt = new SearchExt.Builder(query).setURI("test2/test1/_search").setRestMethod("POST").build();
        //SearchResult searchResult = client.execute(searchExt);
        //System.out.println(searchResult.getJsonString());
        //client.execute(new Update.Builder("{}").index("twitter").type("tweet").id("1").build());

        String source = "{\n" +
                "  \"f\":\"aa3\",\n" +
                "  \"g\":\"g\",\n" +
                "  \"test1\":\"abcdefg\"\n" +
                "}";
        //Index index = new Index.Builder(source).index("twitter").type("tweet").build();
        IndexExt index = new IndexExt.Builder(source).setURI("/test2/test1/1").build();

        DocumentResult result = client.execute(index);
        System.out.println(result.getJsonString());
        return null;
    }

    public static void main(String[] args) throws IOException {
        execute();
    }
}
