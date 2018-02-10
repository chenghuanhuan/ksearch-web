/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.NodeLogService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.node.NodeLogVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月31日 上午10:34 $
 */
@Controller
@RequestMapping("/node")
public class NodeController extends BaseController {

    @Resource
    private NodeLogService nodeLogService;

    @RequestMapping
    public String index(){
        return "node_log";
    }

    @RequestMapping("query")
    @ResponseBody
    public Response query(NodeLogVO nodeLogVO) throws ParseException {
        PageResponse pageResponse =nodeLogService.query(nodeLogVO);
        return pageResponse;
    }
}
