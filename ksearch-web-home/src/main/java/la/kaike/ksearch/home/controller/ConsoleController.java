/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import com.baidu.disconf.client.usertools.IKuKoConfDataGetter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.ClusterRequest;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.bo.ClusterHealthBO;
import la.kaike.ksearch.model.vo.console.ClusterHealthReqVO;
import la.kaike.ksearch.model.vo.console.ClusterNodeReqVO;
import la.kaike.ksearch.model.vo.console.ClusterStatisticsReqVO;
import la.kaike.ksearch.model.vo.elastic.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.action.admin.cluster.node.stats.NodeStats;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:46 $
 */
@Controller
@RequestMapping("/console")
public class ConsoleController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class);
    @Resource
    private ElasticSearchService elasticSearchService;

    private static LoadingCache<String, List<IndicesVO>> cache = null;

    //@RequiresPermissions(value = {"delete"})
    @RequestMapping
    public String index(){
        return "console";
    }

    /**
     * 获取所有的集群名称
     * @return
     */
    @RequestMapping("/clusters")
    @ResponseBody
    public Response getCluster(){
        String clusterNames = IKuKoConfDataGetter.getStringValue("ksearch.cluster.name");
        return succeed(clusterNames);
    }


    /**
     * 集群健康状况
     * @return
     */
    @RequestMapping("/cluster/health")
    @ResponseBody
    public Response clusterHealth(ClusterHealthReqVO clusterHealthReqVO){
        List<ClusterHealthListVO> clusterHealthListVOList = new ArrayList<>();
        String clusterName = clusterHealthReqVO.getClusterName();
        try {
            ClusterHealthBO clusterHealthBO = elasticSearchService.clusterHealth(clusterName);

            Map<String,Object> map =  PropertyUtils.describe(clusterHealthBO);

            for (Map.Entry<String,Object> entry:map.entrySet()){
                ClusterHealthListVO vo = new ClusterHealthListVO();
                if ("class".equals(entry.getKey())){
                    continue;
                }
                vo.setName(entry.getKey());
                vo.setValue(entry.getValue().toString());
                clusterHealthListVOList.add(vo);
            }
        } catch (Exception e) {
            logger.error("clusterHealth error!",e);
            return failed();
        }
        return succeed(clusterHealthListVOList);
    }

    /**
     * 集群节点信息
     * @return
     */
    @RequestMapping("/cluster/state/nodes")
    @ResponseBody
    public Response clusterAndNode(ClusterNodeReqVO clusterNodeReqVO){
        NodesStatsResponse response = elasticSearchService.nodeStats(clusterNodeReqVO.getClusterName());
        ClusterVO clusterVO = new ClusterVO();
        List<NodeStatsVO> nodesStatsVOList = new ArrayList<>();
        List<NodeStats> nodeStatsList = response.getNodes();

        // 获取健康状况
        ClusterHealthBO clusterHealthBO = elasticSearchService.clusterHealth(clusterNodeReqVO.getClusterName());
        clusterVO.setStatus(clusterHealthBO.getStatus());
        clusterVO.setName(response.getClusterName().value());
        if (!CollectionUtils.isEmpty(nodeStatsList)){
            for (NodeStats nodeStats:nodeStatsList){
                NodeStatsVO nodesStatsVO = new NodeStatsVO();
                nodesStatsVO.setName(nodeStats.getNode().getName());
                nodesStatsVO.setHost(nodeStats.getHostname());
                nodesStatsVO.setTransport_address(nodeStats.getNode().getAddress().getHost()+":"+nodeStats.getNode().getAddress().getPort());
                nodesStatsVOList.add(nodesStatsVO);
            }
        }
        clusterVO.setNodes(nodesStatsVOList);
        List<ClusterVO> clusterVOList = new ArrayList<>();
        clusterVOList.add(clusterVO);
        return succeed(clusterVOList);
    }

    /**
     * 集群统计信息
     * @return
     */
    @RequestMapping("/cluster/statistics")
    @ResponseBody
    public Response clusterStatistics(ClusterStatisticsReqVO clusterStatisticsReqVO){
        ClusterStatisticsVO clusterStatisticsVO = elasticSearchService.clusterStatistics(clusterStatisticsReqVO.getClusterName());
        return succeed(clusterStatisticsVO);
    }

    /**
     * 集群索引列表
     * @return
     */
    @RequestMapping("/cluster/indeices")
    @ResponseBody
    public Response indicesList(ClusterRequest request) throws ExecutionException {

        String key = "indeices";
        // 添加缓存
        if (cache==null){
            //indicesVOList = elasticSearchService.getIndicesVO(request.getClusterName());
            cache = CacheBuilder.newBuilder()
                    //设置cache的初始大小为10，要合理设置该值
                    .initialCapacity(10)
                    //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                    .concurrencyLevel(10)
                    //设置cache中的数据在写入之后的存活时间为3秒
                    .expireAfterWrite(1, TimeUnit.HOURS)
                    //构建cache实例
                    .build(new CacheLoader<String, List<IndicesVO> >() {
                        @Override
                        public List<IndicesVO>  load(String key) throws Exception {
                            List<IndicesVO> indicesVOList = elasticSearchService.getIndicesVO(request.getClusterName());
                            return indicesVOList;
                        }
                    });
        }
        List<IndicesVO> indices = cache.get(key);
        return succeed(indices);
    }

    /**
     * 刷新缓存
     * @return
     */
    @RequestMapping("/refresh/cache")
    @ResponseBody
    public Response refreshCache(){
        // 添加缓存
        if (cache!=null){
            cache.refresh("indeices");
        }
        return succeed();
    }
}
