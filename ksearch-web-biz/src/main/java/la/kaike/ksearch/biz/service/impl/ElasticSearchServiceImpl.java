/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import la.kaike.ksearch.biz.es.ElasticClient;
import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.model.bo.ClusterHealthBO;
import la.kaike.ksearch.model.vo.elastic.ClusterStatisticsVO;
import la.kaike.ksearch.model.vo.elastic.IndicesVO;
import la.kaike.ksearch.model.vo.index.AddIndexVO;
import la.kaike.ksearch.model.vo.index.CloseIndexVO;
import la.kaike.ksearch.model.vo.index.DelIndexVO;
import la.kaike.ksearch.model.vo.index.RefreshIndexVO;
import la.kaike.ksearch.util.constant.IndexSettingConstant;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.block.ClusterBlock;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:21 $
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Override
    public ClusterHealthBO clusterHealth(String clusterName) {
        ClusterHealthResponse obj = ElasticClient.newInstance().getClusterHealth();
        ClusterHealthBO clusterHealthBO = new ClusterHealthBO();
        if(obj!=null){
            clusterHealthBO = JSON.parseObject(obj.toString(),ClusterHealthBO.class);
        }

        return clusterHealthBO;
    }

    @Override
    public NodesStatsResponse nodeStats(String clusterName) {
        NodesStatsResponse response = ElasticClient.newInstance().getNodeStats();
        return response;
    }

    @Override
    public ClusterStatisticsVO clusterStatistics(String clusterName) {

        ClusterStatisticsVO clusterStatisticsVO = new ClusterStatisticsVO();

        // 统计文档数量
        IndicesStatsResponse clusterStatsResponse = ElasticClient.newInstance().getStats();
        Map<String, IndexStats> indexStatsMap = clusterStatsResponse.getIndices();
        long totalDoc = 0;
        long bytes =0;
        if (indexStatsMap!=null&&indexStatsMap.size()>0){
            for (Map.Entry<String,IndexStats> entry:indexStatsMap.entrySet()){
                IndexStats indexStats = entry.getValue();
                totalDoc = totalDoc+indexStats.getTotal().getDocs().getCount();
                ByteSizeValue byteSizeValue = indexStats.getPrimaries().getStore().getSize();;
                //indexStats.getPrimaries().getStore().getSize();
                //System.out.println(indexStats.getPrimaries().getTotalMemory());
                //System.out.println("-------"+byteSizeValue);
                bytes = bytes+byteSizeValue.getBytes();
            }
        }
        System.out.println("total:"+bytes);
        ByteSizeValue byteSizeValue = new ByteSizeValue(bytes);
        clusterStatisticsVO.setDocuments(String.valueOf(totalDoc));
        clusterStatisticsVO.setIndices(indexStatsMap.size());
        clusterStatisticsVO.setSize(byteSizeValue.toString());
        clusterStatisticsVO.setTotalShards(clusterStatsResponse.getTotalShards());
        clusterStatisticsVO.setSuccessfulShards(clusterStatsResponse.getSuccessfulShards());
        return clusterStatisticsVO;
    }

    @Override
    public List<IndicesVO> getIndicesVO(String clusterName) {
        List<IndicesVO> indicesVOList = new ArrayList<>();

        /** 正在使用的索引 ***/
        IndicesStatsResponse clusterStatsResponse = ElasticClient.newInstance().getStats();
        MetaData metadata = ElasticClient.newInstance().getMetadata();
        Map<String, IndexStats> indexStatsMap = clusterStatsResponse.getIndices();
        if (indexStatsMap!=null&&indexStatsMap.size()>0){
            for (Map.Entry<String,IndexStats> entry:indexStatsMap.entrySet()){
                IndexStats indexStats = entry.getValue();
                IndicesVO indicesVO = new IndicesVO();
                indicesVO.setIndexName(entry.getKey());
                indicesVO.setDocs(indexStats.getTotal().getDocs().getCount());
                indicesVO.setPrimarySize(indexStats.getPrimaries().getStore().getSize().toString());
                //indicesVO.setShards(indexStats.getShards().length);
                indicesVO.setStatus(indexStats.getPrimaries().getRecoveryStats().toString());

                // 获取MetaData
                IndexMetaData indexMetaData = metadata.index(entry.getKey());
                Settings settings = indexMetaData.getSettings();
                Integer replicas = settings.getAsInt(IndexSettingConstant.NUMBER_OF_REPLICAS,null);
                Integer shards = settings.getAsInt(IndexSettingConstant.NUMBER_OF_SHARDS,null);
                indicesVO.setReplicas(replicas);
                indicesVO.setShards(shards);
                indicesVO.setStatus(indexMetaData.getState().name());
                indicesVOList.add(indicesVO);
            }
        }


        /** 获取已关闭的索引 */

        ClusterStateResponse clusterStateResponse = ElasticClient.newInstance().getClusterState();
        ImmutableOpenMap<String,Set<ClusterBlock>> immutableOpenMap =  clusterStateResponse.getState().getBlocks().indices();
        Iterator<ObjectObjectCursor<String, Set<ClusterBlock>>> iterator = immutableOpenMap.iterator();
        while (iterator.hasNext()) {
            ObjectObjectCursor<String,Set<ClusterBlock>> objectObjectCursor = iterator.next();

            IndicesVO indicesVO = new IndicesVO();

            Set<ClusterBlock> clusterBlockSet = objectObjectCursor.value;
            ClusterBlock clusterBlock = clusterBlockSet.iterator().next();
            indicesVO.setIndexName(objectObjectCursor.key);
            indicesVO.setStatus(clusterBlock.status().name());
            indicesVOList.add(indicesVO);
        }

        return indicesVOList;
    }

    @Override
    public void addIndex(AddIndexVO addIndexVO) {
        TransportClient client = ElasticClient.newInstance().getTransportClient();
        client.admin().indices().prepareCreate(addIndexVO.getIndex()).setSettings(
                Settings.builder()
                        .put(IndexSettingConstant.NUMBER_OF_SHARDS,addIndexVO.getNumberOfShards())
                        .put(IndexSettingConstant.NUMBER_OF_REPLICAS,addIndexVO.getNumberOfReplicas())
        ).get();
    }

    @Override
    public void delIndex(DelIndexVO delIndexVO) {
        TransportClient client = ElasticClient.newInstance().getTransportClient();
        client.admin().indices().prepareDelete(delIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void refreshIndex(RefreshIndexVO refreshIndexVO) {
        ElasticClient.newInstance().getIndicesAdminClient().prepareRefresh(refreshIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void closeIndex(CloseIndexVO closeIndexVO) {
        ElasticClient.newInstance().getIndicesAdminClient().prepareClose(closeIndexVO.getIndices().toArray(new String[]{})).get();
    }


}
