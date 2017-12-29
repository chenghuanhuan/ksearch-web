/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.biz.service.SysLogService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.home.warpper.CacheManager;
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

    private final Pattern indexPattern = Pattern.compile("^([a-zA-Z_0-9\\-]+).([a-zA-Z_0-9\\-]+).(\\d\\d\\d\\d-\\d\\d-\\d\\d)");

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
        String key = simpleQueryReqVO.getClusterName()+"_indeices";
        if (CacheManager.cache==null){
            CacheManager.init(elasticSearchService,simpleQueryReqVO.getClusterName());
        }
        List<IndicesVO> indicesVOS = CacheManager.get(key);


        List<SelectVO> appList = new ArrayList<>();
        List<SelectVO> dateList = new ArrayList<>();
        Map<String,List<SelectVO>> mapMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(indicesVOS)){
            for (IndicesVO indicesVO:indicesVOS){
                Matcher matcher = indexPattern.matcher(indicesVO.getIndex());

                if (matcher.matches()){
                    String appname = matcher.group(2);
                    String date = matcher.group(3);
                    SelectVO selectVO = new SelectVO();
                    selectVO.setId(appname);
                    selectVO.setText(appname);
                    if (!appList.contains(selectVO)){
                        appList.add(selectVO);
                    }
                    SelectVO selectVO2 = new SelectVO();
                    selectVO2.setId(date);
                    selectVO2.setText(date);
                    if (!dateList.contains(selectVO2)){
                        dateList.add(selectVO2);
                    }
                }
            }
            mapMap.put("appList",appList);
            mapMap.put("dateList",dateList);
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
