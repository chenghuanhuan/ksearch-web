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
import la.kaike.ksearch.model.vo.index.*;
import la.kaike.ksearch.util.constant.IndexSettingConstant;
import la.kaike.ksearch.util.exception.BussinessException;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.block.ClusterBlock;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentType;
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
                indicesVO.setIndex(entry.getKey());
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

                ImmutableOpenMap<String,AliasMetaData> aliasMetaMap =  indexMetaData.getAliases();
                Iterator<ObjectObjectCursor<String,AliasMetaData>> objectObjectCursorIterator = aliasMetaMap.iterator();
                List<String> aliases = new ArrayList<>();
                while (objectObjectCursorIterator.hasNext()){
                    ObjectObjectCursor<String,AliasMetaData> dataObjectObjectCursor = objectObjectCursorIterator.next();
                    AliasMetaData aliasMetaData = dataObjectObjectCursor.value;
                    aliases.add(aliasMetaData.getAlias());
                }
                indicesVO.setAliases(aliases);
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
            indicesVO.setIndex(objectObjectCursor.key);
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

    @Override
    public void flushIndex(FlushIndexVO flushIndexVO) {
        ElasticClient.newInstance().getIndicesAdminClient().prepareFlush(flushIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void optimizeIndex(OptimizeIndexVO optimizeIndexVO) {
        ElasticClient.newInstance().getTransportClient().admin().indices()
                .prepareForceMerge(optimizeIndexVO.getIndex())
                .setFlush(optimizeIndexVO.getFlush())
                .setMaxNumSegments(optimizeIndexVO.getMaxNumSegments())
                .setOnlyExpungeDeletes(optimizeIndexVO.getOnlyExpungeDeletes())
                .get();
    }

    @Override
    public void createAlias(CreateAliasVO aliasIndexVO) {
        IndicesAliasesRequestBuilder builder = ElasticClient.newInstance().getIndicesAdminClient().prepareAliases();
        String index = aliasIndexVO.getIndex();
        for (String alias:aliasIndexVO.getAliases()){
            builder.addAlias(index,alias);
        }
        builder.get();
    }

    @Override
    public void delAlias(DelAliasVO delAliasVO) {
        IndicesAliasesRequestBuilder builder = ElasticClient.newInstance().getIndicesAdminClient().prepareAliases();
        builder.removeAlias(delAliasVO.getIndex(),delAliasVO.getAlias()).get();
    }

    @Override
    public void addMapping(AddMappingVO addMappingVO) {
        PutMappingRequestBuilder builder = ElasticClient.newInstance().getIndicesAdminClient()
                .preparePutMapping(addMappingVO.getIndex());
        builder.setType(addMappingVO.getType())
                .setSource(addMappingVO.getMappingsJson(), XContentType.JSON)
                .get();
    }

    @Override
    public List<MappingVO> getAllMapping(GetMappingVO getMappingVO) {
        List<MappingVO> mappingVOList = new ArrayList<>();
        try {
            GetMappingsResponse response = ElasticClient.newInstance().getIndicesAdminClient()
                    .prepareGetMappings(getMappingVO.getIndex()).get();
            ImmutableOpenMap<String,ImmutableOpenMap<String,MappingMetaData>> metaDataImmutableOpenMap = response.getMappings();
            Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String,MappingMetaData>>> iterator = metaDataImmutableOpenMap.iterator();
            while (iterator.hasNext()){
                ObjectObjectCursor<String, ImmutableOpenMap<String,MappingMetaData>>  objectCursor = iterator.next();
                ImmutableOpenMap<String,MappingMetaData> map = objectCursor.value;
                Iterator<ObjectObjectCursor<String,MappingMetaData>> metaDataIterator = map.iterator();

                while (metaDataIterator.hasNext()){
                    ObjectObjectCursor<String,MappingMetaData> mc = metaDataIterator.next();
                    MappingMetaData mappingMetaData = mc.value;

                    // 解析properties
                    Map<String,Object> sourceAsMap = mappingMetaData.getSourceAsMap();
                    MappingVO mappingVO = new MappingVO();
                    mappingVO.setIndex(getMappingVO.getIndex());
                    mappingVO.setType(mc.key);
                    if (sourceAsMap.containsKey("include_in_all")){
                        Boolean include_in_all = (Boolean) sourceAsMap.get("include_in_all");
                        mappingVO.setInclude_in_all(include_in_all);
                    }
                    List<PropertiesVO> properties = mapToProperties(sourceAsMap);
                    mappingVO.setProperties(properties);
                    mappingVOList.add(mappingVO);
                }
            }
        } catch (Exception e) {
            logger.error("getAllMapping error!",e);
            new BussinessException("getAllMapping error!");
        }
        return mappingVOList;
    }

    /**
     * 将map转换成属性对象
     * @param params
     * @return
     */
    private List<PropertiesVO> mapToProperties(Map<String,Object> params){

        List<PropertiesVO> propertiesVOList = new ArrayList<>();
        for (Map.Entry<String,Object> entrySet:params.entrySet()){

            Object value = entrySet.getValue();
            if (value instanceof LinkedHashMap){
                Map<String,Object> linkedHashMap = (LinkedHashMap) value;
                // 载入属性值
                try {
                    for (Map.Entry<String,Object> entry:linkedHashMap.entrySet()){
                        PropertiesVO propertiesVO = new PropertiesVO();
                        if (entry.getValue() instanceof LinkedHashMap){
                            Map<String,Object> v = (Map<String, Object>) entry.getValue();
                            BeanUtils.populate(propertiesVO,v);
                            if (v.containsKey("properties")){
                                //Map<String,Object> children = (Map<String, Object>) v.get("properties");
                                List<PropertiesVO> properties = mapToProperties(v);
                                propertiesVO.setType("object");
                                propertiesVO.setChildren(properties);
                            }
                        }

                        propertiesVO.setName(entry.getKey());
                        propertiesVOList.add(propertiesVO);

                    }

                } catch (Exception e) {
                    logger.error("mapToProperties error!",e);
                    new BussinessException("mapToProperties error!");
                }
            }
        }
        return propertiesVOList;
    }

}
