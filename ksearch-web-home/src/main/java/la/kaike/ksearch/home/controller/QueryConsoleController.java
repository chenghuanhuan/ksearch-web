/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.query.ExecuteReqVO;
import la.kaike.ksearch.util.constant.MethodNameEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:59 $
 */
@Controller
@RequestMapping("/query")
public class QueryConsoleController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(QueryConsoleController.class);
    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping
    public String index(){
        return "query_console";
    }

    @RequestMapping("query_dsl")
    public String queryDsl(){
        return "query_dsl";
    }

    /**
     * 执行查询语句
     * @param executeReqVO
     * @return
     */
    @RequestMapping("/execute")
    @ResponseBody
    public Response execute(ExecuteReqVO executeReqVO) throws IOException {

        String method=null;
        String firstLine=null;
        String dsl=null;
        String uri=null;
        // 校验
        String query = executeReqVO.getQuery();
        int position = query.indexOf("\n");
        if (position<0){
            firstLine = query;
            method = firstLine.substring(0, query.indexOf(" "));
            uri = firstLine.substring(query.indexOf(" "), firstLine.length()).trim();
        }else {
            firstLine = query.substring(0, position);

            method = firstLine.substring(0, query.indexOf(" "));
            if (StringUtils.isEmpty(method)) {
                return failed("格式错误，没有请求method");
            }
            method = method.toUpperCase();
            if (!MethodNameEnum.GET.getValue().equals(method)
                    && !MethodNameEnum.PUT.getValue().equals(method)
                    && !MethodNameEnum.POST.getValue().equals(method)
                    && !MethodNameEnum.DELETE.getValue().equals(method)) {
                return failed("格式错误，请求method 不正确，method有：GET、PUT、POST、DELETE");
            }

            uri = firstLine.substring(query.indexOf(" "), firstLine.length()).trim();
            uri = uri.replace("\n", "");

            dsl = query.substring(query.indexOf("\n"), query.length()).trim();
            logger.info("====dsl=====");
            logger.info(dsl);

            logger.info("====uri=====");
            logger.info(firstLine);
        }
        String res = elasticSearchService.executeDSL(method,uri,dsl,executeReqVO.getClusterName());
        return succeed(res);
    }

    public static void main(String[] args) {
        String query ="sffsfsff\n sffsf\n";
        String firstLine = query.substring(0,query.indexOf("\n"));
        System.out.println(query);
    }
}
