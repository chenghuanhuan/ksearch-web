/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.NginxLogService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.nginx.NginxErrorLogVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年10月25日 下午2:52 $
 */
@Controller
@RequestMapping("/nginx")
public class NginxLogController extends BaseController{

    @Resource
    private NginxLogService nginxLogService;

    @RequestMapping
    public String index(){
        return "nginx_error_log";
    }

    /**
     * 查询错误日志
     * @return
     */
    @RequestMapping("/error/list")
    @ResponseBody()
    public Response errorList(NginxErrorLogVO nginxErrorLogVO) throws ParseException {
        if (StringUtils.isNotEmpty(nginxErrorLogVO.getTimestamp())) {
            String uploadDate = nginxErrorLogVO.getTimestamp();
            String[] arr = uploadDate.split(" - ");
            String startTime = arr[0];
            String endTime = arr[1];
            nginxErrorLogVO.setStartTime(startTime);
            nginxErrorLogVO.setEndTime(endTime);
        }
        PageResponse pageResponse = nginxLogService.errorList(nginxErrorLogVO);
        return succeed(pageResponse);
    }

    /**
     * 查询错误日志
     * @return
     */
    @RequestMapping("/access/list")
    @ResponseBody()
    public Response accessList(){
        return succeed();
    }
}
