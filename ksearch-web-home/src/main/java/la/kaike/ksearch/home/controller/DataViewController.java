/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.query.SimpleQueryReqVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:59 $
 */
@Controller
@RequestMapping("/dataview")
public class DataViewController extends BaseController{

    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping
    public String index(){
        return "dataview";
    }

    /**
     * 查询
     * @param simpleQueryReqVO
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public Response query(SimpleQueryReqVO simpleQueryReqVO) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        PageResponse pageResponse = elasticSearchService.simpleQuery(simpleQueryReqVO);
        return succeed(pageResponse);
    }

}
