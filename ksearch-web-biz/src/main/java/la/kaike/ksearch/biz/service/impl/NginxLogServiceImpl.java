/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.NginxLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.nginx.NginxErrorLogVO;
import la.kaike.platform.common.lang.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

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


        // 类型
        builder.setTypes("error_log");

        // 排序
        if (nginxErrorLogVO.getSort()!=null && nginxErrorLogVO.getOrder()!=null){
            builder.addSort(nginxErrorLogVO.getSort(),SortOrder.valueOf(nginxErrorLogVO.getOrder().toUpperCase()));
        }

        if (nginxErrorLogVO.getConnectionId()!=null){
            builder.setQuery(QueryBuilders.matchQuery("nginx.error.connection_id",nginxErrorLogVO.getConnectionId()));
        }


        if (StringUtils.isNotEmpty(nginxErrorLogVO.getHostname())){
            builder.setQuery(QueryBuilders.termQuery("beat.hostname",nginxErrorLogVO.getHostname()));
        }

        if (StringUtils.isNotEmpty(nginxErrorLogVO.getTimestamp())){
            builder.setQuery(rangeQuery("@timestamp").gte(DateUtils.parseDate(nginxErrorLogVO.getStartTime(),"yyyy-MM-dd HH:mm:ss").getTime())
                    .lte(DateUtils.parseDate(nginxErrorLogVO.getEndTime(),"yyyy-MM-dd HH:mm:ss").getTime()));
        }

        if (StringUtils.isNotEmpty(nginxErrorLogVO.getMessage())){
            builder.setQuery(matchQuery("message",nginxErrorLogVO.getMessage()));
        }


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

}
