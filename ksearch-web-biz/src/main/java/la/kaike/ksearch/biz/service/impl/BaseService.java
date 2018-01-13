/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.BaseRequest;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.PageVO;
import la.kaike.ksearch.util.annotations.ESQuery;
import la.kaike.ksearch.util.util.ClassUtils;
import la.kaike.ksearch.util.util.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年12月15日 下午12:04 $
 */
public class BaseService {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected PageResponse get(SearchRequestBuilder builder, PageVO pageVO){
        PageResponse pageResponse = new PageResponse();
        try {

            // 查询总条数
            SearchResponse countRes = builder.setSize(0).get();
            if (StringUtils.isNotEmpty(pageVO.getSort())) {
                builder.addSort(pageVO.getSort(), SortOrder.valueOf(pageVO.getOrder().toUpperCase()));
            }

            pageResponse.setTotal(countRes.getHits().getTotalHits());
            if (countRes.getHits().getTotalHits()>0) {

                // 查询真实数据
                builder.setFrom(pageVO.getOffset())
                        .setSize(pageVO.getLimit());

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

    protected BoolQueryBuilder query(BaseRequest request,Class<?> clazz){
        BoolQueryBuilder boolQueryBuilder = boolQuery();

        Set<Field> fieldSet =  ClassUtils.getAllFiled(clazz);
        for (Field field:fieldSet){
            ESQuery query = field.getAnnotation(ESQuery.class);
            if (query!=null){

                Object value;
                String fieldName = query.field();
                if (StringUtils.isEmpty(fieldName)){
                    fieldName = field.getName();
                }

                switch (query.type()){
                    case keyword:
                        // 获取值
                        value = ClassUtils.getFieldValue(request,field.getName());
                        if (!StringUtils.isBlank((String)value)){
                            boolQueryBuilder.filter(termQuery(fieldName,value));
                        }
                        break;

                    case text:
                        value = ClassUtils.getFieldValue(request,field.getName());
                        if (!StringUtils.isBlank((String)value)) {
                            boolQueryBuilder.must(QueryBuilders.matchQuery(fieldName, value));
                        }
                        break;

                    case Long:
                        value = ClassUtils.getFieldValue(request,field.getName());
                        if (value!=null){
                            boolQueryBuilder.filter(termQuery(fieldName,value));
                        }
                        break;

                    case Integer:
                        value = ClassUtils.getFieldValue(request,field.getName());
                        if (value!=null){
                            boolQueryBuilder.filter(termQuery(fieldName,value));
                        }
                        break;
                    case Date:
                        value =  ClassUtils.getFieldValue(request,field.getName());
                        if (value!=null){
                            try {
                                String datetime = (String) value;
                                String[] arr = datetime.split(" - ");
                                String startTime = arr[0];
                                String endTime = arr[1];
                                String format = query.format();
                                boolQueryBuilder.filter(rangeQuery(fieldName).gte(startTime)
                                        .lte(endTime).format(format));
                            }catch (Exception e){
                                logger.error("时间查询字段解析错误：value={}",value,e);
                            }
                        }
                }
            }
        }

        return boolQueryBuilder;
    }
}
