/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.SysLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.syslog.SysLogVO;
import la.kaike.ksearch.util.constant.WebConstant;
import la.kaike.platform.common.lang.DateUtils;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年11月16日 下午5:40 $
 */
@Service
public class SysLogServiceImpl implements SysLogService{


    @Override
    public PageResponse query(SysLogVO sysLogVO) throws ParseException {

        PageResponse pageResponse = new PageResponse();

        TransportClient client = ElasticClient.getClient(sysLogVO.getClusterName());

        SearchRequestBuilder builder = client.prepareSearch(WebConstant.SYSLOG_PREFIX+sysLogVO.getIndex());
        builder.setTypes(sysLogVO.getType());

        BoolQueryBuilder boolQueryBuilder = boolQuery();

        /***********查询条件************/
        if (StringUtils.isNotEmpty(sysLogVO.getClassName())){
            boolQueryBuilder.filter(matchQuery("className",sysLogVO.getClassName()));
        }

        if (StringUtils.isNotEmpty(sysLogVO.getLevel())){
            boolQueryBuilder.filter(termQuery("level",sysLogVO.getLevel()));
        }

        if (StringUtils.isNotEmpty(sysLogVO.getMessage())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("message",sysLogVO.getMessage()));
        }

        if (StringUtils.isNotEmpty(sysLogVO.getHost())){
            boolQueryBuilder.filter(termQuery("host",sysLogVO.getHost()));
        }

        if (StringUtils.isNotEmpty(sysLogVO.getContextId())){
            boolQueryBuilder.filter(termQuery("contextId",sysLogVO.getContextId()));
        }

        if (StringUtils.isNotEmpty(sysLogVO.getEndTime())&&StringUtils.isNotEmpty(sysLogVO.getStartTime())){
            boolQueryBuilder.filter(rangeQuery("datetime").gte(DateUtils.parseDate(sysLogVO.getStartTime(),"yyyy-MM-dd HH:mm:ss").getTime())
                    .lte(DateUtils.parseDate(sysLogVO.getEndTime(),"yyyy-MM-dd HH:mm:ss").getTime()));
        }
        /******************************/



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
