/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.HotFixService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.hotfix.HotFixVO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月12日 下午5:43 $
 */
@Service
public class HotFixServiceImpl extends BaseService implements HotFixService{


    @Override
    public PageResponse query(HotFixVO hotFixVO) {

        TransportClient client = ElasticClient.getClient(hotFixVO.getClusterName());
        SearchRequestBuilder builder = client.prepareSearch("hotfix.log.*");
        builder.setTypes("log");
        BoolQueryBuilder boolQueryBuilder = super.query(hotFixVO,hotFixVO.getClass());

        if (StringUtils.isNotEmpty(hotFixVO.getEndTime())&&StringUtils.isNotEmpty(hotFixVO.getStartTime())){
            boolQueryBuilder.filter(rangeQuery("datetime").gte(hotFixVO.getStartTime())
                    .lte(hotFixVO.getEndTime()).format("yyyy/MM/dd HH:mm:ss"));
        }


        builder.setQuery(boolQueryBuilder);
        // 查询总条数
        PageResponse pageResponse = get(builder,hotFixVO.getOffset(),hotFixVO.getLimit());

        return pageResponse;
    }
}
