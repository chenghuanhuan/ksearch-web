/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.NginxLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.nginx.NginxAccessLogVO;
import la.kaike.ksearch.model.vo.nginx.NginxErrorLogVO;
import la.kaike.ksearch.util.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年10月25日 下午3:26 $
 */
@Service
public class NginxLogServiceImpl implements NginxLogService {
    @Override
    public PageResponse errorList(NginxErrorLogVO nginxErrorLogVO) throws ParseException {


        PageResponse pageResponse = new PageResponse();

        TransportClient client = ElasticClient.getClient(nginxErrorLogVO.getClusterName());

        SearchRequestBuilder builder = null;

        // 索引
        builder = client.prepareSearch("nginx_error_log_*");
        BoolQueryBuilder boolQueryBuilder = boolQuery();

        // 类型
        builder.setTypes("error_log");

        // 排序
        if (nginxErrorLogVO.getSort()!=null && nginxErrorLogVO.getOrder()!=null){
            builder.addSort(nginxErrorLogVO.getSort(),SortOrder.valueOf(nginxErrorLogVO.getOrder().toUpperCase()));
        }

        if (nginxErrorLogVO.getConnectionId()!=null){
            boolQueryBuilder.must((QueryBuilders.matchQuery("nginx.error.connection_id",nginxErrorLogVO.getConnectionId())));
        }


        if (StringUtils.isNotEmpty(nginxErrorLogVO.getHostname())){
            boolQueryBuilder.must((QueryBuilders.termQuery("beat.hostname",nginxErrorLogVO.getHostname())));
        }

        if (StringUtils.isNotEmpty(nginxErrorLogVO.getTimestamp())){
            Calendar start = DateUtil.toUTCTime(nginxErrorLogVO.getStartTime());
            Calendar end = DateUtil.toUTCTime(nginxErrorLogVO.getEndTime());
            boolQueryBuilder.must(rangeQuery("@timestamp").gte(start.getTimeInMillis())
                    .lte(end.getTimeInMillis()));
        }

        if (StringUtils.isNotEmpty(nginxErrorLogVO.getMessage())){
            boolQueryBuilder.must(matchQuery("nginx.error.message",nginxErrorLogVO.getMessage()));
        }

        if (StringUtils.isNotEmpty(nginxErrorLogVO.getLevel())){
            boolQueryBuilder.must(matchQuery("nginx.error.level",nginxErrorLogVO.getLevel()));
        }


        builder.setQuery(boolQueryBuilder);
        SearchResponse countRes = builder.setSize(0).get();
        pageResponse.setTotal(countRes.getHits().getTotalHits());
        List<Map<String, Object>> hashMapList = new ArrayList<>();

        // 查询数据
        if (countRes.getHits().getTotalHits()>0) {

            builder.setFrom(nginxErrorLogVO.getOffset())
                    .setSize(nginxErrorLogVO.getLimit());
            SearchResponse searchResponse = builder.get();
            SearchHits searchHits = searchResponse.getHits();


            Iterator<SearchHit> searchHitIterator = searchHits.iterator();
            while (searchHitIterator.hasNext()) {
                SearchHit hit = searchHitIterator.next();
                Map<String, Object> map = hit.getSource();
                hashMapList.add(map);
            }
        }
        pageResponse.setRows(hashMapList);
        return pageResponse;

    }

    @Override
    public PageResponse accessList(NginxAccessLogVO accessLogVO) throws ParseException {

        PageResponse pageResponse = new PageResponse();

        TransportClient client = ElasticClient.getClient(accessLogVO.getClusterName());

        // 索引
        SearchRequestBuilder builder = client.prepareSearch("nginx_access_log_*");

        BoolQueryBuilder boolQueryBuilder = boolQuery();
        // 类型
        builder.setTypes("access_log");

        // 排序
        if (accessLogVO.getSort()!=null && accessLogVO.getOrder()!=null){
            builder.addSort(accessLogVO.getSort(),SortOrder.valueOf(accessLogVO.getOrder().toUpperCase()));
        }


        if (StringUtils.isNotEmpty(accessLogVO.getHostname())){
            boolQueryBuilder.must(termQuery("beat.hostname",accessLogVO.getHostname()));
        }

        if (StringUtils.isNotEmpty(accessLogVO.getTimestamp())){
            Calendar start = DateUtil.toUTCTime(accessLogVO.getStartTime());
            Calendar end = DateUtil.toUTCTime(accessLogVO.getEndTime());
            boolQueryBuilder.must(rangeQuery("@timestamp").gte(start.getTimeInMillis())
                    .lte(end.getTimeInMillis()));
        }

        if (StringUtils.isNotEmpty(accessLogVO.getMessage())){
            boolQueryBuilder.must(matchQuery("message",accessLogVO.getMessage()));
        }

        if (StringUtils.isNotEmpty(accessLogVO.getSource())){
            boolQueryBuilder.must(matchQuery("source",accessLogVO.getSource()));
        }

        if (StringUtils.isNotEmpty(accessLogVO.getIp())){
            boolQueryBuilder.must(termQuery("nginx.access.geoip.ip",accessLogVO.getIp()));
        }

        if (StringUtils.isNotEmpty(accessLogVO.getResponseCode())){
            boolQueryBuilder.must(termQuery("nginx.access.response_code",accessLogVO.getResponseCode()));
        }

        builder.setQuery(boolQueryBuilder);
        SearchResponse countRes = builder.setSize(0).get();
        pageResponse.setTotal(countRes.getHits().getTotalHits());
        List<Map<String, Object>> hashMapList = new ArrayList<>();

        // 查询数据
        if (countRes.getHits().getTotalHits()>0) {

            builder.setFrom(accessLogVO.getOffset())
                    .setSize(accessLogVO.getLimit());
            SearchResponse searchResponse = builder.get();
            SearchHits searchHits = searchResponse.getHits();


            Iterator<SearchHit> searchHitIterator = searchHits.iterator();
            while (searchHitIterator.hasNext()) {
                SearchHit hit = searchHitIterator.next();
                Map<String, Object> map = hit.getSource();
                hashMapList.add(map);
            }
        }
        pageResponse.setRows(hashMapList);
        return pageResponse;
    }

}
