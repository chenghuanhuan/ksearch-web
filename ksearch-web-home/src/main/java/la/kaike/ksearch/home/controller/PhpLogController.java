/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.PhpLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.phplog.PhpLogVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年12月15日 上午10:05 $
 */
@Controller
@RequestMapping("/phplog")
public class PhpLogController {

    @Autowired
    private PhpLogService phpLogService;

    @RequestMapping
    public String index(){
        return "php_log";
    }


    @RequestMapping("query")
    @ResponseBody
    public Response query(PhpLogVO phpLogVO) throws ParseException {
        if (StringUtils.isNotEmpty(phpLogVO.getDatetime())) {
            String uploadDate = phpLogVO.getDatetime();
            String[] arr = uploadDate.split(" - ");
            String startTime = arr[0];
            String endTime = arr[1];
            phpLogVO.setStartTime(startTime);
            phpLogVO.setEndTime(endTime);
        }

        PageResponse pageResponse = phpLogService.query(phpLogVO);

        return pageResponse;
    }

}
