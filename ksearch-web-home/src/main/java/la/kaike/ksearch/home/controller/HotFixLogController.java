/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.HotFixService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.hotfix.HotFixVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月12日 下午5:33 $
 */
@Controller
@RequestMapping("/hotfix")
public class HotFixLogController {

    @Autowired
    private HotFixService hotFixService;

    @RequestMapping
    public String index(){
        return "hotfix_log";
    }

    @RequestMapping("query")
    @ResponseBody
    public Response query(HotFixVO hotFixVO) throws ParseException {
        PageResponse pageResponse = hotFixService.query(hotFixVO);
        return pageResponse;
    }
}
