/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.biz.service.SysLogService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.SelectVO;
import la.kaike.ksearch.model.vo.elastic.IndicesVO;
import la.kaike.ksearch.model.vo.query.SimpleQueryReqVO;
import la.kaike.ksearch.model.vo.syslog.SysLogVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年11月16日 下午5:23 $
 */
@Controller
@RequestMapping("/tomcat")
public class TomcatController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    private final Pattern indexPattern = Pattern.compile("^([a-zA-Z_0-9\\-]+).([a-zA-Z_0-9\\-]+).([a-zA-Z_0-9\\-]+).(\\d\\d\\d\\d-\\d\\d-\\d\\d)");

    @RequestMapping
    public String index(){
        return "tomcat_log";
    }

    @RequestMapping("query")
    @ResponseBody
    public Response query(SysLogVO sysLogVO) throws ParseException {

        PageResponse pageResponse = sysLogService.query(sysLogVO);
        return pageResponse;
    }

    /**
     * 获取应用名称
     * @param simpleQueryReqVO
     * @return
     */
    @RequestMapping("/appname")
    @ResponseBody
    public Response indexSelect(SimpleQueryReqVO simpleQueryReqVO){
        List<IndicesVO> indicesVOS =  elasticSearchService.getIndicesVO(simpleQueryReqVO.getClusterName());
        List<SelectVO> selectVOList = new ArrayList<>();

        Map<String,Map<String,Set<String>>> mapMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(indicesVOS)){
            for (IndicesVO indicesVO:indicesVOS){
                Matcher matcher = indexPattern.matcher(indicesVO.getIndex());

                if (matcher.matches()){
                    String appname = matcher.group(2);
                    String logType = matcher.group(3);
                    String date = matcher.group(4);
                    if (mapMap.get(appname)!=null){
                        Map<String,Set<String>> logTypeMap = mapMap.get(appname);
                        if (logTypeMap.get(logType)!=null){
                            Set<String> dateset = logTypeMap.get(logType);
                            dateset.add(date);
                        }
                    }else {
                        Map<String,Set<String>> logTypeMap = new HashMap<>();
                        Set<String> dateset = new HashSet<>();
                        dateset.add(date);
                        logTypeMap.put(logType,dateset);
                        mapMap.put(appname,logTypeMap);
                    }
                }
            }
        }
        return succeed(mapMap);
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^([a-zA-Z_0-9\\-]+).([a-zA-Z_0-9\\-]+).([a-zA-Z_0-9\\-]+).(\\d\\d\\d\\d-\\d\\d-\\d\\d)");
        String index = "ksearch-api.ksearch-api-biz-service.2017-11-16";
        Matcher matcher = pattern.matcher(index);
        System.out.println(matcher.find());
    }
}
