/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.NodeLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.node.NodeLogVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月31日 下午12:03 $
 */
@Service
public class NodeLogServieImpl extends BaseService implements NodeLogService {
    @Override
    public PageResponse query(NodeLogVO nodeLogVO) {
        TransportClient client = ElasticClient.getClient(nodeLogVO.getClusterName());
        SearchRequestBuilder builder = client.prepareSearch("nodejs.log.*");
        builder.setTypes("log");
        BoolQueryBuilder boolQueryBuilder = super.query(nodeLogVO,nodeLogVO.getClass());
        builder.setQuery(boolQueryBuilder);
        // 查询总条数
        PageResponse pageResponse = get(builder,nodeLogVO);

        return pageResponse;
    }
}
