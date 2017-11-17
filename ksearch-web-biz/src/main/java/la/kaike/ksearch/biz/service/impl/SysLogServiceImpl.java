/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.SysLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.syslog.SysLogVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年11月16日 下午5:40 $
 */
@Service
public class SysLogServiceImpl implements SysLogService{


    @Override
    public PageResponse query(SysLogVO sysLogVO) {

        PageResponse pageResponse = new PageResponse();

        TransportClient client = ElasticClient.getClient(sysLogVO.getClusterName());

        SearchRequestBuilder builder = client.prepareSearch(sysLogVO.getIndex());
        builder.setTypes(sysLogVO.getType());

        BoolQueryBuilder boolQueryBuilder = boolQuery();

        builder.setQuery(boolQueryBuilder);
        //builder.setFetchSource(new String[]{},new String[]{"contentData"});
        //builder.setSource(SearchSourceBuilder)
        // 查询总条数
        SearchResponse countRes = builder.setSize(0).get();

        builder.addSort("datetime", SortOrder.DESC);

        pageResponse.setTotal(countRes.getHits().getTotalHits());
        if (countRes.getHits().getTotalHits()>0) {

            // 查询真实数据
            builder.setFrom(sysLogVO.getOffset())
                    .setSize(sysLogVO.getLimit());

            SearchResponse searchResponse = builder.get();
            SearchHits searchHits = searchResponse.getHits();

            Iterator<SearchHit> searchHitIterator = searchHits.iterator();
            List<Map<String, Object>> hashMapList = new ArrayList<>();
            while (searchHitIterator.hasNext()) {
                SearchHit hit = searchHitIterator.next();
                Map<String, Object> map = hit.getSource();
                hashMapList.add(map);
            }
            pageResponse.setRows(hashMapList);
        }
        return pageResponse;
    }
}
