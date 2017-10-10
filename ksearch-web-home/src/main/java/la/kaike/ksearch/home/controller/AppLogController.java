/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.AppLogService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.bo.applog.AppLogBO;
import la.kaike.ksearch.model.vo.applog.AppLogIdVO;
import la.kaike.ksearch.model.vo.applog.AppLogVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;

/**
 * app日志查询管理
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月28日 下午3:00 $
 */
@Controller
@RequestMapping("/applog")
public class AppLogController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(AppLogController.class);

    @Resource
    private AppLogService appLogService;

    @RequestMapping
    public String index(){
        return "applog";
    }

    /**
     * 查询日志列表
     * @param appLogVO
     * @return
     */
    @RequestMapping("query")
    @ResponseBody
    public Response query(AppLogVO appLogVO) throws ParseException {
        if (StringUtils.isNotEmpty(appLogVO.getUploadDate())) {
            String uploadDate = appLogVO.getUploadDate();
            String[] arr = uploadDate.split(" - ");
            String startTime = arr[0];
            String endTime = arr[1];
            appLogVO.setStartTime(startTime);
            appLogVO.setEndTime(endTime);
        }

        PageResponse pageResponse = appLogService.query(appLogVO);
        return pageResponse;
    }

    /**
     * 日志详情
     * @param appLogIdVO
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Response detail(AppLogIdVO appLogIdVO) throws Exception {
        AppLogBO appLogBO = appLogService.queryById(appLogIdVO);
        return succeed(appLogBO);
    }

    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppLogIdVO appLogIdVO = new AppLogIdVO();
        String id = request.getParameter("id");
        String clusterName = request.getParameter("clusterName");
        appLogIdVO.setId(id);
        appLogIdVO.setClusterName(clusterName);
        AppLogBO appLogBO = appLogService.queryById(appLogIdVO);

        String fileName = appLogBO.getClientToken()+"_"+appLogBO.getUploadDate();
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        try {
            response.setContentType("application/octet-stream");
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {   //IE浏览器
                fileName = URLEncoder.encode(fileName + ".log", "UTF-8");
            } else {
                fileName = URLDecoder.decode(fileName + ".log","UTF-8");//其他浏览器
            }

            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1")); // 指定下载的文件名
            os.write(appLogBO.getContentData().getBytes("UTF-8"));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("download fail ",e);
        } finally {
            if(os != null){
                os.close();
            }
        }

    }
}
