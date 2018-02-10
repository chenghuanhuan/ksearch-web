/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.youqianhuan.ksearch.model.vo.index.*;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import com.youqianhuan.ksearch.BaseRequest;
import com.youqianhuan.ksearch.biz.es.ElasticClient;
import com.youqianhuan.ksearch.biz.es.ElasticClientUtil;
import com.youqianhuan.ksearch.biz.es.IndexExt;
import com.youqianhuan.ksearch.biz.es.SearchExt;
import com.youqianhuan.ksearch.biz.service.ElasticSearchService;
import com.youqianhuan.ksearch.biz.support.ESQueryVOStore;
import com.youqianhuan.ksearch.model.PageResponse;
import com.youqianhuan.ksearch.model.bo.ClusterHealthBO;
import com.youqianhuan.ksearch.model.vo.elastic.ClusterStatisticsVO;
import com.youqianhuan.ksearch.model.vo.elastic.IndicesVO;
import com.youqianhuan.ksearch.model.vo.query.SimpleQueryReqVO;
import com.youqianhuan.ksearch.model.vo.query.SortFieldVO;
import com.youqianhuan.ksearch.util.constant.IndexSettingConstant;
import com.youqianhuan.ksearch.util.constant.MethodNameEnum;
import com.youqianhuan.ksearch.util.exception.BussinessException;
import com.youqianhuan.ksearch.util.util.DateUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午7:21 $
 */
@Service
public class ElasticSearchServiceImpl extends BaseService implements ElasticSearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Override
    public ClusterHealthBO clusterHealth(String clusterName) {
        ClusterHealthResponse obj = ElasticClientUtil.getClusterHealth(ElasticClient.getClient(clusterName));
        ClusterHealthBO clusterHealthBO = new ClusterHealthBO();
        if(obj!=null){
            clusterHealthBO = JSON.parseObject(obj.toString(),ClusterHealthBO.class);
        }

        return clusterHealthBO;
    }

    @Override
    public NodesStatsResponse nodeStats(String clusterName) {
        NodesStatsResponse response = ElasticClientUtil.getNodeStats(ElasticClient.getClient(clusterName));
        return response;
    }

    @Override
    public ClusterStatisticsVO clusterStatistics(String clusterName) {

        ClusterStatisticsVO clusterStatisticsVO = new ClusterStatisticsVO();

        // 统计文档数量
        IndicesStatsResponse clusterStatsResponse = ElasticClientUtil.getStats(ElasticClient.getClient(clusterName));
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
        IndicesStatsResponse clusterStatsResponse = ElasticClientUtil.getStats(ElasticClient.getClient(clusterName));
        MetaData metadata = ElasticClientUtil.getMetadata(ElasticClient.getClient(clusterName));
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
                if (indexMetaData != null){
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
                }

                indicesVOList.add(indicesVO);
            }
        }


        /** 获取已关闭的索引 */

        ClusterStateResponse clusterStateResponse = ElasticClientUtil.getClusterState(ElasticClient.getClient(clusterName));
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
    public void addIndex(AddIndexReqVO addIndexVO) {
        TransportClient client = ElasticClient.getClient(addIndexVO.getClusterName());
        String defaultSetting = "{" +
                "    \"analysis\":{" +
                    "    \"analyzer\": {" +
                    "        \"ik_en_max_word\": {" +
                    "          \"type\": \"custom\"," +
                    "          \"char_filter\":  [\"html_strip\"]," +
                    "          \"tokenizer\": \"ik_max_word\"," +
                    "          \"filter\": [" +
                    "            \"stemmer\"," +
                    "            \"stop\"" +
                    "          ]" +
                    "        }," +
                    "        \"ik_en_smart\": {" +
                    "          \"type\": \"custom\"," +
                    "          \"char_filter\":  [\"html_strip\"]," +
                    "          \"tokenizer\": \"ik_smart\"," +
                    "          \"filter\": [" +
                    "            \"stemmer\"," +
                    "            \"stop\"" +
                    "          ]" +
                    "        }," +
                    "        \"keyword_lowercase\": {" +
                    "          \"type\": \"custom\"," +
                    "          \"tokenizer\": \"keyword\"," +
                    "          \"filter\": [" +
                    "            \"lowercase\"" +
                    "          ]" +
                    "        }" +
                    "      }"+
                "    }" +
                "}";
        Map<String,Object> setting = JSON.parseObject(defaultSetting,Map.class);
        setting.put("number_of_shards",addIndexVO.getNumberOfShards());
        setting.put("number_of_replicas",addIndexVO.getNumberOfReplicas());
        client.admin().indices().prepareCreate(addIndexVO.getIndex()).setSettings(JSON.toJSONString(setting),XContentType.JSON).get();
        // 设置索引不动态添加字段映射
        //client.admin().indices().prepareUpdateSettings(addIndexVO.getIndex()).setSettings("{\"index.mapper.dynamic\":false}",XContentType.JSON);

    }

    @Override
    public void delIndex(DelIndexReqVO delIndexVO) {
        TransportClient client = ElasticClient.getClient(delIndexVO.getClusterName());
        client.admin().indices().prepareDelete(delIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void refreshIndex(RefreshIndexReqVO refreshIndexVO) {
        ElasticClient.getClient(refreshIndexVO.getClusterName()).admin()
                .indices().prepareRefresh(refreshIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void closeIndex(CloseIndexReqVO closeIndexVO) {
        ElasticClient.getClient(closeIndexVO.getClusterName()).admin()
                .indices().prepareClose(closeIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void openIndex(OpenIndexReqVO openIndexReqVO) {
        ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(openIndexReqVO.getClusterName()))
                .prepareOpen(openIndexReqVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void flushIndex(FlushIndexReqVO flushIndexVO) {
        ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(flushIndexVO.getClusterName()))
                .prepareFlush(flushIndexVO.getIndices().toArray(new String[]{})).get();
    }

    @Override
    public void optimizeIndex(OptimizeIndexReqVO optimizeIndexVO) {
        ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(optimizeIndexVO.getClusterName()))
                .prepareForceMerge(optimizeIndexVO.getIndex())
                .setFlush(optimizeIndexVO.getFlush())
                .setMaxNumSegments(optimizeIndexVO.getMaxNumSegments())
                .setOnlyExpungeDeletes(optimizeIndexVO.getOnlyExpungeDeletes())
                .get();
    }

    @Override
    public void createAlias(CreateAliasReqVO aliasIndexVO) {
        IndicesAliasesRequestBuilder builder = ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(aliasIndexVO.getClusterName())).prepareAliases();
        String index = aliasIndexVO.getIndex();
        for (String alias:aliasIndexVO.getAliases()){
            builder.addAlias(index,alias);
        }
        builder.get();
    }

    @Override
    public void delAlias(DelAliasReqVO delAliasVO) {
        IndicesAliasesRequestBuilder builder = ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(delAliasVO.getClusterName())).prepareAliases();
        builder.removeAlias(delAliasVO.getIndex(),delAliasVO.getAlias()).get();
    }

    @Override
    public void addMapping(AddMappingReqVO addMappingVO) {
        PutMappingRequestBuilder builder = ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(addMappingVO.getClusterName()))
                .preparePutMapping(addMappingVO.getIndex());
        builder.setType(addMappingVO.getType())
                .setSource(addMappingVO.getMappingsJson(), XContentType.JSON)
                .get();

        /*String template = "{" +
                "    \"template\" : \"te*\"," +
                "    \"settings\" : {" +
                "        \"number_of_shards\" : 1" +
                "    }," +
                "    \"aliases\" : {" +
                "        \"alias1\" : {}," +
                "        \"alias2\" : {" +
                "            \"filter\" : {" +
                "                \"term\" : {\"user\" : \"kimchy\" }" +
                "            }," +
                "            \"routing\" : \"kimchy\"" +
                "        }," +
                "        \"{index}-alias\" : {} " +
                "    }" +
                "}";
        ElasticClient.getClient(addMappingVO.getClusterName()).admin().indices().preparePutTemplate("template1")
                .setSource(template.getBytes(),XContentType.JSON).get();*/
        //TODO 添加模板功能
    }

    @Override
    public List<MappingVO> getAllMapping(GetMappingReqVO getMappingVO) {
        List<MappingVO> mappingVOList = new ArrayList<>();
        try {

            GetMappingsRequest request = new GetMappingsRequest();
            request.indices(getMappingVO.getIndex());
            if (getMappingVO.getType()!=null) {
                request.types(getMappingVO.getType());
            }
            GetMappingsResponse response = ElasticClientUtil.getIndicesAdminClient(ElasticClient.getClient(getMappingVO.getClusterName()))
                    .getMappings(request).actionGet();

           /* GetMappingsResponse response = ElasticClient.newInstance().getIndicesAdminClient()
                    .prepareGetMappings(getMappingVO.getIndex()).get();*/


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
                    if (sourceAsMap.containsKey("dynamic")){
                        mappingVO.setDynamic((String) sourceAsMap.get("dynamic"));
                    }
                    //String ss = mappingMetaData.source().toString();
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

    @Override
    public PageResponse simpleQuery(SimpleQueryReqVO simpleQueryReqVO) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Object pageVO = null;
        Class<?> clazz = null;
        // 参数转换
        if (StringUtils.isNotEmpty(simpleQueryReqVO.getSource())){
            String className = ESQueryVOStore.getClassName(simpleQueryReqVO.getIndices().get(0),simpleQueryReqVO.getTypes().get(0));
            clazz = Class.forName(className);
            pageVO = JSON.parseObject(simpleQueryReqVO.getSource(),clazz);
        }




        PageResponse pageResponse = new PageResponse();

        TransportClient client = ElasticClient.getClient(simpleQueryReqVO.getClusterName());

        SearchRequestBuilder builder = builder(client,simpleQueryReqVO);

        if (StringUtils.isNotEmpty(simpleQueryReqVO.getSource())) {
            BoolQueryBuilder boolQueryBuilder = super.query((BaseRequest) pageVO, clazz);
            builder.setQuery(boolQueryBuilder);
        }

        SearchResponse countRes = builder.setSize(0).get();
        pageResponse.setTotal(countRes.getHits().getTotalHits());


        // 查询真实数据
        //builder = builder(client,simpleQueryReqVO);

        builder.setFrom(simpleQueryReqVO.getOffset())
                .setSize(simpleQueryReqVO.getLimit());
        SearchResponse searchResponse = builder.get();
        SearchHits searchHits = searchResponse.getHits();



        Iterator<SearchHit> searchHitIterator = searchHits.iterator();
        List<Map<String,Object>> hashMapList = new ArrayList<>();
        while (searchHitIterator.hasNext()){
            SearchHit hit = searchHitIterator.next();
            Map<String,Object> map = hit.getSource();
            hashMapList.add(map);
        }
        pageResponse.setRows(hashMapList);
        return pageResponse;
    }

    private SearchRequestBuilder builder(TransportClient client,SimpleQueryReqVO simpleQueryReqVO){
        SearchRequestBuilder builder = null;

        // 索引
        if (CollectionUtils.isEmpty(simpleQueryReqVO.getIndices())){
            builder = client.prepareSearch();
        }else {
            builder = client.prepareSearch(simpleQueryReqVO.getIndices().toArray(new String[]{}));
        }

        // 类型
        if (CollectionUtils.isNotEmpty(simpleQueryReqVO.getTypes())){
            builder.setTypes(simpleQueryReqVO.getTypes().toArray(new String[]{}));
        }
        // 排序字段
        if (CollectionUtils.isNotEmpty(simpleQueryReqVO.getSorts())){
            for (SortFieldVO sortFieldVO:simpleQueryReqVO.getSorts()){
                builder.addSort(sortFieldVO.getField(), SortOrder.valueOf(sortFieldVO.getOrder().toUpperCase()));
            }
        }

        if (simpleQueryReqVO.getSort()!=null && simpleQueryReqVO.getOrder()!=null){
            builder.addSort(simpleQueryReqVO.getSort(),SortOrder.valueOf(simpleQueryReqVO.getOrder().toUpperCase()));
        }

        if (StringUtils.isNotEmpty(simpleQueryReqVO.getKeyword())){
            //builder.setQuery(QueryBuilders.queryStringQuery(simpleQueryReqVO.getKeyword()));
            builder.setQuery(QueryBuilders.simpleQueryStringQuery(simpleQueryReqVO.getKeyword()));
            //builder.setQuery(QueryBuilders.termQuery("type",simpleQueryReqVO.getKeyword()));
            //builder.setQuery()
        }

        if (StringUtils.isNotEmpty(simpleQueryReqVO.getSource())) {
            builder.setQuery(QueryBuilders.wrapperQuery(simpleQueryReqVO.getSource()));
            logger.debug(simpleQueryReqVO.getSource());
        }

        return builder;
    }

    @Override
    public String addDoc(AddDocReqVO addDocReqVO) {
        ElasticClient.getClient(addDocReqVO.getClusterName())
                .prepareIndex(addDocReqVO.getIndex(),addDocReqVO.getType())
                .setSource(addDocReqVO.getJsonSource(),XContentType.JSON).get();
        return null;
    }

    @Override
    public String executeDSL(String method,String uri,String dsl,String clusterName) throws IOException {
        JestResult jestResult = null;
        JestClient client = ElasticClient.getHttpClient(clusterName);
        if (MethodNameEnum.PUT.getValue().equals(method)||MethodNameEnum.POST.getValue().equals(method)){
            // 校验权限
            SecurityUtils.getSecurityManager().checkPermission(SecurityUtils.getSubject().getPrincipals(),"edit");
            jestResult = postAndPut(dsl,uri,client);
        }else {
            SearchExt searchExt = new SearchExt.Builder(dsl).setURI(uri).setRestMethod(method).build();
            jestResult = client.execute(searchExt);
        }
        return jestResult.getJsonString();
    }

    @Override
    public String clearData(ClearDataReqVO clearDataReqVO) throws IOException {
        JestClient jestClient = ElasticClient.getHttpClient(clearDataReqVO.getClusterName());
        String dsl = "{" +
                "  \"query\": {" +
                "    \"match_all\": {}" +
                "  }" +
                "}";
        String url = "/"+clearDataReqVO.getIndex()+"/"+clearDataReqVO.getType()+"/_delete_by_query";
        SearchExt searchExt = new SearchExt.Builder(dsl).setURI(url).setRestMethod(MethodNameEnum.POST.getValue()).build();
        String res = jestClient.execute(searchExt).getJsonString();
        return res;
    }

    @Override
    public void clearOldIndex(String clusterName) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar nowC = Calendar.getInstance();
        logger.info("当前日期:"+sdf.format(nowC.getTime()));
        nowC.add(Calendar.DAY_OF_MONTH,-7);

        List<IndicesVO> indicesVOList = getIndicesVO(clusterName);

        /*******************删除nginx日志******************/

     /*   // 过滤出需要删除的index
        if (indicesVOList!=null&& indicesVOList.size()>0){
            // 删除小于此月份的所有索引
            List<String> deleteIndexList = new ArrayList<>();
            for (int i=indicesVOList.size()-1;i>=0;i--){
                IndicesVO indicesVO = indicesVOList.get(i);
                String index = indicesVO.getIndex();
                if (index.startsWith("nginx_access_log_")||index.startsWith("nginx_error_log_")){
                    String [] strs = index.split("log_");
                    String strDate = strs[1];
                    Date date = sdf.parse(strDate);
                    if (date.getTime()<=nowC.getTimeInMillis()){
                        deleteIndexList.add(index);
                        indicesVOList.remove(indicesVO);
                    }
                }
            }

            logger.info(deleteIndexList.toString());
            if (CollectionUtils.isNotEmpty(deleteIndexList)) {
                TransportClient client = ElasticClient.getClient(clusterName);
                client.admin().indices().prepareDelete(deleteIndexList.toArray(new String[]{})).get();
            }
        }*/
        /*********************删除系统日志**************************/

        //ksearch-api.ksearch-api-biz-service.2017-11-20
        String reg = "(\\d\\d\\d\\d-\\d\\d-\\d\\d)";
        List<String> delSysLogIndexList = filterIndex(indicesVOList,reg,null);

        logger.info("删除索引："+delSysLogIndexList);
        if (CollectionUtils.isNotEmpty(delSysLogIndexList)) {
            TransportClient client = ElasticClient.getClient(clusterName);
            String [] indexes = delSysLogIndexList.toArray(new String[]{});
            client.admin().indices().prepareDelete(indexes).get();
        }

        String reg2 = "(\\d\\d\\d\\d.\\d\\d.\\d\\d)";
        List<String> delSysLogIndexList2 = filterIndex(indicesVOList,reg2,"yyyy.MM.dd");

        logger.info("删除索引："+delSysLogIndexList2);
        if (CollectionUtils.isNotEmpty(delSysLogIndexList2)) {
            TransportClient client = ElasticClient.getClient(clusterName);
            String [] indexes = delSysLogIndexList2.toArray(new String[]{});
            client.admin().indices().prepareDelete(indexes).get();
        }
    }

    // 过滤出需要删除的索引
    private List<String> filterIndex(List<IndicesVO> indicesVOList,String regix,String format){
        List<String> deleteIndexList = new ArrayList<>();


        Calendar nowC = Calendar.getInstance();
        // 七天前的时间
        nowC.add(Calendar.DAY_OF_MONTH,-7);


        Pattern pattern = Pattern.compile(regix);
        if (indicesVOList!=null&& indicesVOList.size()>0) {
            for (int i=indicesVOList.size()-1;i>=0;i--) {
                IndicesVO indicesVO = indicesVOList.get(i);
                String index = indicesVO.getIndex();
                Matcher matcher = pattern.matcher(index);
                if (matcher.find()&&matcher.groupCount()>0) {
                    Date date;
                    try {
                        if (format==null) {
                            date = DateUtils.parseDate(matcher.group(1),"yyyy-MM-dd");
                        }else {
                            date = DateUtils.parseDate(matcher.group(1),format);
                        }
                    } catch (ParseException e) {
                        logger.error("filterIndex error 日志格式错误",e);
                        continue;
                    }
                    if (date.getTime() <= nowC.getTimeInMillis()) {
                        deleteIndexList.add(index);
                        indicesVOList.remove(indicesVO);
                    }
                }
            }
        }

        return deleteIndexList;
    }

    private JestResult postAndPut(String dsl,String uri,JestClient client) throws IOException {
        //method = MethodNameEnum.POST.getValue();
        IndexExt index = new IndexExt.Builder(dsl).setURI(uri).build();
        JestResult jestResult = client.execute(index);
        return  jestResult;
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
                                //propertiesVO.setType("object");
                                if (!"nested".equals(propertiesVO.getType())){
                                    propertiesVO.setType("object");
                                }
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
