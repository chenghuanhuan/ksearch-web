/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.controller;

import com.youqianhuan.ksearch.biz.service.ElasticSearchService;
import com.youqianhuan.ksearch.biz.support.CustomPropertyPlaceholderConfigurer;
import com.youqianhuan.ksearch.home.base.BaseController;
import com.youqianhuan.ksearch.home.warpper.CacheManager;
import com.youqianhuan.ksearch.model.ClusterRequest;
import com.youqianhuan.ksearch.model.Response;
import com.youqianhuan.ksearch.model.bo.ClusterHealthBO;
import com.youqianhuan.ksearch.model.vo.console.ClusterHealthReqVO;
import com.youqianhuan.ksearch.model.vo.console.ClusterNodeReqVO;
import com.youqianhuan.ksearch.model.vo.console.ClusterStatisticsReqVO;
import com.youqianhuan.ksearch.model.vo.elastic.*;
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

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:46 $
 */
@Controller
@RequestMapping("/console")
public class ConsoleController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class);
    @Resource
    private ElasticSearchService elasticSearchService;



    //@RequiresPermissions(value = {"delete"})
    @RequestMapping
    public String index(){
        return "console";
    }

    @Resource
    private CustomPropertyPlaceholderConfigurer configurer;

    /**
     * 获取所有的集群名称
     * @return
     */
    @RequestMapping("/clusters")
    @ResponseBody
    public Response getCluster(){
        String clusterNames = configurer.getProperty("ksearch.cluster.name");
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
    public Response indicesList(ClusterRequest request) {

        String key = request.getClusterName()+"_indeices";
        // 添加缓存
        if (CacheManager.cache==null) {
            CacheManager.init(elasticSearchService);
        }
        List<IndicesVO> indices = CacheManager.get(key);
        return succeed(indices);
    }

    /**
     * 刷新缓存
     * @return
     */
    @RequestMapping("/refresh/cache")
    @ResponseBody
    public Response refreshCache(ClusterRequest request){
        String key = request.getClusterName()+"_indeices";
        // 添加缓存
        if (CacheManager.cache!=null){
            CacheManager.cache.refresh(key);
        }
        return succeed();
    }
}
