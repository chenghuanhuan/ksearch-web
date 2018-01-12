/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.EwtLogService;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.ewtlog.EwtLogVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月02日 上午10:14 $
 */
@Controller
@RequestMapping("/ewtlog")
public class EwtLogController {

    private static final Logger logger = LoggerFactory.getLogger(EwtLogController.class);

    @Autowired
    private EwtLogService ewtLogService;

    @RequestMapping
    public String index(){
        return "ewtlog";
    }


    @RequestMapping("query")
    @ResponseBody
    public Response query(EwtLogVO ewtLogVO){

        logger.info("一网通日志查询 params={}",ewtLogVO);
        if (StringUtils.isNotEmpty(ewtLogVO.getDatetime())) {
            String uploadDate = ewtLogVO.getDatetime();
            String[] arr = uploadDate.split(" - ");
            String startTime = arr[0];
            String endTime = arr[1];
            ewtLogVO.setStartTime(startTime);
            ewtLogVO.setEndTime(endTime);
        }

        PageResponse pageResponse = ewtLogService.query(ewtLogVO);

        return pageResponse;
    }

}
