/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.EwtLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.ewtlog.EwtLogVO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月02日 下午2:09 $
 */
@Service
public class EwtLogServiceImpl extends BaseService implements EwtLogService {

    private static final Logger logger = LoggerFactory.getLogger(EwtLogServiceImpl.class);
    @Override
    public PageResponse query(EwtLogVO ewtLogVO) {

        logger.info("====================一网通日志查询 start============================");
        TransportClient client = ElasticClient.getClient(ewtLogVO.getClusterName());

        SearchRequestBuilder builder = client.prepareSearch("ewt.access.*");
        builder.setTypes("log");


        BoolQueryBuilder boolQueryBuilder = boolQuery();

        /***********查询条件************/
        logger.info("====================一网通日志查询 组装条件开始============================");
        if (StringUtils.isNotEmpty(ewtLogVO.getAppname())){
            boolQueryBuilder.filter(termQuery("fields.appname",ewtLogVO.getAppname()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getSource())){
            boolQueryBuilder.filter(termQuery("source",ewtLogVO.getSource()));
        }

        if (ewtLogVO.getUserId()!=null){
            boolQueryBuilder.filter(termQuery("userId",ewtLogVO.getUserId()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getParams())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("params",ewtLogVO.getParams()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getUrl())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("url",ewtLogVO.getUrl()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getMethod())){
            boolQueryBuilder.must(QueryBuilders.termQuery("method",ewtLogVO.getMethod()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getRemoteIp())){
            boolQueryBuilder.filter(termQuery("remoteIp",ewtLogVO.getRemoteIp()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getHostname())){
            boolQueryBuilder.filter(termQuery("beat.hostname",ewtLogVO.getHostname()));
        }

        if (StringUtils.isNotEmpty(ewtLogVO.getEndTime())&&StringUtils.isNotEmpty(ewtLogVO.getStartTime())){
            boolQueryBuilder.filter(rangeQuery("datetime").gte(ewtLogVO.getStartTime())
                    .lte(ewtLogVO.getEndTime()).format("yyyy/MM/dd HH:mm:ss"));
        }
        logger.info("====================一网通日志查询 组装条件结束============================");
        /******************************/


        builder.setQuery(boolQueryBuilder);
        // 查询总条数
        PageResponse pageResponse = get(builder,ewtLogVO);

        logger.info("====================一网通日志查询 end============================");
        return pageResponse;
    }
}
