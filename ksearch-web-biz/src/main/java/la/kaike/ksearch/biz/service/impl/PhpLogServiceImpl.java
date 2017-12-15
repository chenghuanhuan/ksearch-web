/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.PhpLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.phplog.PhpLogVO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年12月15日 上午11:55 $
 */
@Service
public class PhpLogServiceImpl extends BaseService implements PhpLogService{
    @Override
    public PageResponse query(PhpLogVO phpLogVO) throws ParseException {


        TransportClient client = ElasticClient.getClient(phpLogVO.getClusterName());

        SearchRequestBuilder builder = client.prepareSearch("php-error*");
        builder.setTypes("log");

        BoolQueryBuilder boolQueryBuilder = boolQuery();

        /***********查询条件************/

        if (StringUtils.isNotEmpty(phpLogVO.getLevel())){
            boolQueryBuilder.filter(termQuery("level",phpLogVO.getLevel()));
        }

        if (StringUtils.isNotEmpty(phpLogVO.getSource())){
            boolQueryBuilder.filter(termQuery("source",phpLogVO.getSource()));
        }

        if (StringUtils.isNotEmpty(phpLogVO.getMsg())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("msg",phpLogVO.getMsg()));
        }

        if (StringUtils.isNotEmpty(phpLogVO.getMsgp())){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("msg",phpLogVO.getMsgp()));
        }

        if (phpLogVO.getPid()!=null){
            boolQueryBuilder.filter(matchQuery("pid",phpLogVO.getPid()));
        }

        if (phpLogVO.getTimestamp()!=null){
            boolQueryBuilder.filter(matchQuery("timestamp",phpLogVO.getTimestamp()));
        }

        if (StringUtils.isNotEmpty(phpLogVO.getHostname())){
            boolQueryBuilder.filter(termQuery("beat.hostname",phpLogVO.getHostname()));
        }

        if (StringUtils.isNotEmpty(phpLogVO.getEndTime())&&StringUtils.isNotEmpty(phpLogVO.getStartTime())){
            boolQueryBuilder.filter(rangeQuery("datetime").gte(phpLogVO.getStartTime())
                    .lte(phpLogVO.getEndTime()).format("yyyy/MM/dd HH:mm:ss"));
        }
        /******************************/



        builder.setQuery(boolQueryBuilder);
        //builder.setFetchSource(new String[]{},new String[]{"contentData"});
        //builder.setSource(SearchSourceBuilder)
        // 查询总条数
        PageResponse pageResponse = get(builder,phpLogVO.getOffset(),phpLogVO.getLimit());
        return pageResponse;
    }
}
