/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.AppLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.bo.applog.AppLogBO;
import la.kaike.ksearch.model.vo.applog.AppLogIdVO;
import la.kaike.ksearch.model.vo.applog.AppLogVO;
import la.kaike.ksearch.util.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月28日 下午3:59 $
 */
@Service
public class AppLogServiceImpl implements AppLogService {

    private static final String INDEX = "applog";

    private static final String TYPE = "applog";

    @Override
    public PageResponse query(AppLogVO appLogVO) {
        PageResponse pageResponse = new PageResponse();

        TransportClient client = ElasticClient.getClient(appLogVO.getClusterName());

        SearchRequestBuilder builder = client.prepareSearch("applog");
        builder.setTypes("applog");

        BoolQueryBuilder boolQueryBuilder = boolQuery();
        if (StringUtils.isNotEmpty(appLogVO.getVersion())){
            boolQueryBuilder.filter(prefixQuery("version",appLogVO.getVersion()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getOsVersion())){
            boolQueryBuilder.filter(prefixQuery("osVersion",appLogVO.getOsVersion()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getClientToken())){
            boolQueryBuilder.filter(matchQuery("clientToken",appLogVO.getClientToken()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getBundleIdentifier())){
            boolQueryBuilder.filter(termQuery("bundleIdentifier",appLogVO.getBundleIdentifier()));
        }

        if (appLogVO.getPlatform()!=null){
            boolQueryBuilder.filter(rangeQuery("platform").gte(appLogVO.getPlatform()).lte(appLogVO.getPlatform()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getContentData())){
            //boolQueryBuilder.filter(matchQuery("contentData",appLogVO.getContentData()));
            boolQueryBuilder.must(matchQuery("contentData",appLogVO.getContentData()));
            boolQueryBuilder.should(matchPhraseQuery("contentData",appLogVO.getContentData()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getUploadDate())){
            boolQueryBuilder.filter(rangeQuery("uploadDate").gte(appLogVO.getStartTime()).lte(appLogVO.getEndTime()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getBrand())){
            boolQueryBuilder.filter(prefixQuery("brand",appLogVO.getBrand()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getResolution())){
            boolQueryBuilder.filter(termQuery("resolution",appLogVO.getResolution()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getChannel())){
            boolQueryBuilder.filter(termQuery("channel",appLogVO.getChannel()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getDeviceModel())){
            boolQueryBuilder.filter(prefixQuery("deviceModel",appLogVO.getDeviceModel()));
        }

        if (StringUtils.isNotEmpty(appLogVO.getImei())){
            boolQueryBuilder.filter(termQuery("imei",appLogVO.getImei()));
        }


        //QueryBuilder qb = boolQuery()
                //.must(termQuery("content", "test1"))
                //.must(termQuery("content", "test4"))
                //.mustNot(termQuery("content", "test2"))
                //.should(termQuery("content", "test3"))
                //.filter(termQuery("content", "test5"))
                //.filter(prefixQuery("version","ver"));
        builder.setQuery(boolQueryBuilder);
        builder.setFetchSource(new String[]{},new String[]{"contentData"});
        //builder.setSource(SearchSourceBuilder)
        // 查询总条数
        SearchResponse countRes = builder.setSize(0).get();
        pageResponse.setTotal(countRes.getHits().getTotalHits());
        if (countRes.getHits().getTotalHits()>0) {

            // 查询真实数据
            builder.setFrom(appLogVO.getOffset())
                    .setSize(appLogVO.getLimit());

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

    @Override
    public AppLogBO queryById(AppLogIdVO appLogIdVO) throws Exception {
        TransportClient client = ElasticClient.getClient(appLogIdVO.getClusterName());
        GetResponse response = client.prepareGet(INDEX,TYPE,appLogIdVO.getId()).get();
        Map<String,Object> objectMap = response.getSourceAsMap();
        AppLogBO appLogBO = BeanUtil.mapToObject(objectMap,AppLogBO.class);
        return appLogBO;
    }
}
