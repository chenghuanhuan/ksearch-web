/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.AppLogService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.applog.AppLogVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * app日志查询管理
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月28日 下午3:00 $
 */
@Controller
@RequestMapping("/applog")
public class AppLogController extends BaseController {

    @Resource
    private AppLogService appLogService;

    @RequestMapping
    public String index(){
        return "applog";
    }

    @RequestMapping("query")
    @ResponseBody
    public Response query(AppLogVO appLogVO){
        PageResponse pageResponse = appLogService.query(appLogVO);
        return pageResponse;
    }
}
