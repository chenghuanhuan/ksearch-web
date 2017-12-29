/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.home.warpper.CacheManager;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.bo.index.PropertiesBO;
import la.kaike.ksearch.model.vo.index.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:57 $
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping
    public String index(){
        return "index";
    }

    /**
     * 添加索引
     * @param addIndexVO
     * @return
     */
    @RequiresPermissions({"add"})
    @RequestMapping("/add")
    @ResponseBody
    public Response add(AddIndexReqVO addIndexVO){
        elasticSearchService.addIndex(addIndexVO);
        String key = addIndexVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("添加索引成功");
    }

    /**
     * 删除索引
     * @param delIndexVO
     * @return
     */
    @RequiresPermissions({"delete"})
    @RequestMapping("/del")
    @ResponseBody
    public Response del(DelIndexReqVO delIndexVO){
        elasticSearchService.delIndex(delIndexVO);
        String key = delIndexVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("删除索引成功！");
    }


    /**
     * 刷新索引
     * @param refreshIndexVO
     * @return
     */
    @RequiresPermissions({"edit"})
    @RequestMapping("/refresh")
    @ResponseBody
    public Response refresh(RefreshIndexReqVO refreshIndexVO){
        elasticSearchService.refreshIndex(refreshIndexVO);
        String key = refreshIndexVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("刷新索引成功！");
    }

    /**
     * 关闭索引
     * @param closeIndexVO
     * @return
     */
    @RequiresPermissions({"edit"})
    @RequestMapping("/close")
    @ResponseBody
    public Response refresh(CloseIndexReqVO closeIndexVO){
        elasticSearchService.closeIndex(closeIndexVO);
        String key = closeIndexVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("关闭索引成功！");
    }

    /**
     * 打开索引
     * @param openIndexReqVO
     * @return
     */
    @RequestMapping("/open")
    @RequiresPermissions({"edit"})
    @ResponseBody
    public Response refresh(OpenIndexReqVO openIndexReqVO){
        elasticSearchService.openIndex(openIndexReqVO);
        String key = openIndexReqVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("打开索引成功！可能不会马上显示，过一会儿尝试刷新页面！");
    }

    /**
     * flush
     * @param flushIndexVO
     * @return
     */
    @RequiresPermissions({"edit"})
    @RequestMapping("/flush")
    @ResponseBody
    public Response flush(FlushIndexReqVO flushIndexVO){
        elasticSearchService.flushIndex(flushIndexVO);
        String key = flushIndexVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("flush成功！");
    }

    /**
     * 优化索引
     * @param optimizeIndexVO
     * @return
     */
    @RequiresPermissions({"edit"})
    @RequestMapping("/optimize")
    @ResponseBody
    public Response optimize(OptimizeIndexReqVO optimizeIndexVO){
        elasticSearchService.optimizeIndex(optimizeIndexVO);
        String key = optimizeIndexVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("优化成功！");
    }


    @RequiresPermissions({"delete"})
    @RequestMapping("/clearData")
    @ResponseBody
    public Response clearData(ClearDataReqVO clearDataReqVO) throws IOException {
        elasticSearchService.clearData(clearDataReqVO);
        String key = clearDataReqVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("清空成功！");
    }

    /**
     * 添加别名
     * @param createAliasVO
     * @return
     */
    @RequiresPermissions({"add"})
    @RequestMapping("/addAlias")
    @ResponseBody
    public Response addAlias(CreateAliasReqVO createAliasVO){
        elasticSearchService.createAlias(createAliasVO);
        String key = createAliasVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("添加别名成功！");
    }

    /**
     * 删除别名
     * @param delAliasVO
     * @return
     */
    @RequiresPermissions({"delete"})
    @RequestMapping("/delAlias")
    @ResponseBody
    public Response delAlias(DelAliasReqVO delAliasVO){
        elasticSearchService.delAlias(delAliasVO);
        String key = delAliasVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("删除别名成功！");
    }

    /**
     * 添加类型
     * @param addMappingVO
     * {"properties":{"hhh":{"type":"integer","analyzer":"standard","index":"on"}},"include_in_all":false}
     * @return
     */
    @RequiresPermissions({"add"})
    @RequestMapping("/addMapping")
    @ResponseBody
    public Response addMapping(AddMappingReqVO addMappingVO){
        // TODO 校验


        List<PropertiesVO> propertiesVOList = JSONArray.parseArray(addMappingVO.getMappingsJson(),PropertiesVO.class);
        JSONObject jsonObject = new JSONObject();
        JSONObject properties = toPropertiesJson(propertiesVOList);
        jsonObject.put("properties",properties);
        jsonObject.put("dynamic",addMappingVO.getDynamic());
        jsonObject.put("include_in_all",addMappingVO.getInclude_in_all());
        logger.info(jsonObject.toJSONString());
        addMappingVO.setMappingsJson(jsonObject.toJSONString());
        elasticSearchService.addMapping(addMappingVO);
        String key = addMappingVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("保存成功！");
    }

    /**
     * 前端json转换成es配置
     * @param propertiesVOList
     * @return
     */
    private JSONObject toPropertiesJson(List<PropertiesVO> propertiesVOList){
        JSONObject properties = new JSONObject();
        for (PropertiesVO propertiesVO:propertiesVOList){
            PropertiesBO propertiesBO = new PropertiesBO();
            BeanUtils.copyProperties(propertiesVO,propertiesBO);
            if ("".equals(propertiesBO.getAnalyzer())){
                propertiesBO.setAnalyzer(null);
            }
            if ("object".equals(propertiesBO.getType())){
                propertiesBO.setIndex(null);
                propertiesBO.setFielddata(null);
                propertiesBO.setStore(null);
            }
            if ("".equals(propertiesBO.getNull_value())){
                propertiesBO.setNull_value(null);
            }

            if ("nested".equals(propertiesBO.getType())){
                propertiesBO.setStore(null);
                propertiesBO.setFielddata(null);
            }

            if (!"text".equals(propertiesBO.getType())){
                propertiesBO.setFielddata(null);
            }

            if (!CollectionUtils.isEmpty(propertiesVO.getChildren())){
                JSONObject child = toPropertiesJson(propertiesVO.getChildren());
                propertiesBO.setProperties(child);
            }

            properties.put(propertiesVO.getName(),propertiesBO);
        }
        return properties;
    }

    /**
     * 获取所有mapping配置
     * @param getMappingVO
     * @return
     */
    @RequestMapping("/getAllMapping")
    @ResponseBody
    public Response getAllMapping(GetMappingReqVO getMappingVO){
        List<MappingVO> mappings = elasticSearchService.getAllMapping(getMappingVO);
        return succeed(mappings);
    }

    /**
     * 添加文档
     * @param addDocReqVO
     * @return
     */
    @RequiresPermissions({"add"})
    @RequestMapping("/addDoc")
    @ResponseBody
    public Response addDoc(AddDocReqVO addDocReqVO){
        elasticSearchService.addDoc(addDocReqVO);
        String key = addDocReqVO.getClusterName()+"_indeices";
        CacheManager.refresh(key);
        return succeed("保存成功");
    }

    /**
     * 添加类型页面
     * @return
     */
    @RequestMapping("/addTypeHtml")
    public String addTypeHtml(){
        return "addType";
    }
    /**
     * 添加类型页面
     * @return
     */
    @RequestMapping("/addTypeForm")
    public String addTypeForm(){
        return "addTypeForm";
    }

}
