/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.bo.ClusterHealthBO;
import la.kaike.ksearch.model.vo.elastic.ClusterHealthListVO;
import la.kaike.ksearch.model.vo.elastic.ClusterVO;
import la.kaike.ksearch.model.vo.elastic.NodeStatsVO;
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
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:46 $
 */
@Controller
@RequestMapping("/console")
public class ConsoleController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class);
    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping
    public String index(){
        return "console";
    }

    @RequestMapping("/cluster/health")
    @ResponseBody
    public Response clusterHealth(){
        List<ClusterHealthListVO> clusterHealthListVOList = new ArrayList<>();

        try {
            ClusterHealthBO clusterHealthBO = elasticSearchService.clusterHealth("");

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

    @RequestMapping("/cluster/state/nodes")
    @ResponseBody
    public Response clusterAndNode(){
        NodesStatsResponse response = elasticSearchService.nodeStats("");
        ClusterVO clusterVO = new ClusterVO();
        List<NodeStatsVO> nodesStatsVOList = new ArrayList<>();
        List<NodeStats> nodeStatsList = response.getNodes();

        // 获取健康状况
        ClusterHealthBO clusterHealthBO = elasticSearchService.clusterHealth("");
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

}
