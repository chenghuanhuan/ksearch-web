/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.bo.index.PropertiesBO;
import la.kaike.ksearch.model.vo.index.*;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
    @RequestMapping("/add")
    @ResponseBody
    public Response add(AddIndexVO addIndexVO){
        elasticSearchService.addIndex(addIndexVO);
        return succeed("添加索引成功");
    }

    /**
     * 删除索引
     * @param delIndexVO
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public Response del(DelIndexVO delIndexVO){
        elasticSearchService.delIndex(delIndexVO);
        return succeed("删除索引成功！");
    }


    /**
     * 刷新索引
     * @param refreshIndexVO
     * @return
     */
    @RequestMapping("/refresh")
    @ResponseBody
    public Response refresh(RefreshIndexVO refreshIndexVO){
        elasticSearchService.refreshIndex(refreshIndexVO);
        return succeed("刷新索引成功！");
    }

    /**
     * 关闭索引
     * @param closeIndexVO
     * @return
     */
    @RequestMapping("/close")
    @ResponseBody
    public Response refresh(CloseIndexVO closeIndexVO){
        elasticSearchService.closeIndex(closeIndexVO);
        return succeed("关闭索引成功！");
    }

    /**
     * flush
     * @param flushIndexVO
     * @return
     */
    @RequestMapping("/flush")
    @ResponseBody
    public Response flush(FlushIndexVO flushIndexVO){
        elasticSearchService.flushIndex(flushIndexVO);
        return succeed("flush成功！");
    }

    /**
     * 优化索引
     * @param optimizeIndexVO
     * @return
     */
    @RequestMapping("/optimize")
    @ResponseBody
    public Response optimize(OptimizeIndexVO optimizeIndexVO){
        elasticSearchService.optimizeIndex(optimizeIndexVO);
        return succeed("优化成功！");
    }


    /**
     * 添加别名
     * @param createAliasVO
     * @return
     */
    @RequestMapping("/addAlias")
    @ResponseBody
    public Response addAlias(CreateAliasVO createAliasVO){
        elasticSearchService.createAlias(createAliasVO);
        return succeed("添加别名成功！");
    }

    /**
     * 删除别名
     * @param delAliasVO
     * @return
     */
    @RequestMapping("/delAlias")
    @ResponseBody
    public Response delAlias(DelAliasVO delAliasVO){
        elasticSearchService.delAlias(delAliasVO);
        return succeed("删除别名成功！");
    }

    /**
     * 添加类型
     * @param addMappingVO
     * {"properties":{"hhh":{"type":"integer","analyzer":"standard","index":"on"}},"include_in_all":false}
     * @return
     */
    @RequestMapping("/addMapping")
    @ResponseBody
    public Response addMapping(AddMappingVO addMappingVO){
        // TODO 校验


        List<PropertiesVO> propertiesVOList = JSONArray.parseArray(addMappingVO.getMappingsJson(),PropertiesVO.class);
        JSONObject jsonObject = new JSONObject();
        JSONObject properties = toPropertiesJson(propertiesVOList);
        jsonObject.put("properties",properties);
        jsonObject.put("include_in_all",addMappingVO.getInclude_in_all());

        logger.info(jsonObject.toJSONString());
        addMappingVO.setMappingsJson(jsonObject.toJSONString());
        elasticSearchService.addMapping(addMappingVO);
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
            if (!CollectionUtils.isEmpty(propertiesVO.getChildren())){
                JSONObject child = toPropertiesJson(propertiesVO.getChildren());
                propertiesBO.setProperties(child);
            }

            properties.put(propertiesVO.getName(),propertiesBO);
        }
        return properties;
    }

    @RequestMapping("/getAllMapping")
    @ResponseBody
    public Response getAllMapping(GetMappingVO getMappingVO){
        List<MappingVO> mappings = elasticSearchService.getAllMapping(getMappingVO);
        return succeed(mappings);
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

    public static void main(String[] args) throws IOException {
        XContentBuilder builder =  XContentFactory.jsonBuilder()
                .startObject()
                .startObject("news")
                .startObject("properties")
                .startObject("title")
                .field("analyzer", "whitespace")
                .field("type", "string")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        System.out.println(builder.string());

    }
}
