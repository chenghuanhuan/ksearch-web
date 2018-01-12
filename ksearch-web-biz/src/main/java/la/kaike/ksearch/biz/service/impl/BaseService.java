/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.model.PageResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年12月15日 下午12:04 $
 */
public class BaseService {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected PageResponse get(SearchRequestBuilder builder,int offset,int limit){
        PageResponse pageResponse = new PageResponse();
        try {

            // 查询总条数
            SearchResponse countRes = builder.setSize(0).get();

            builder.addSort("datetime", SortOrder.DESC);

            pageResponse.setTotal(countRes.getHits().getTotalHits());
            if (countRes.getHits().getTotalHits()>0) {

                // 查询真实数据
                builder.setFrom(offset)
                        .setSize(limit);

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
        }catch (Exception e){
            logger.error("get error!",e);
        }


        return pageResponse;
    }
}
