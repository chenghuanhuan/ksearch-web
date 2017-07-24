/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.index.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:57 $
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{

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
     * @return
     */
    @RequestMapping("/addMapping")
    @ResponseBody
    public Response addMapping(AddMappingVO addMappingVO){
        elasticSearchService.addMapping(addMappingVO);
        return succeed("保存成功！");
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
