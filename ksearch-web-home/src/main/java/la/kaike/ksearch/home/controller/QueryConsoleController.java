/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午1:59 $
 */
@Controller
@RequestMapping("/query")
public class QueryConsoleController {
    @RequestMapping
    public String index(){
        return "query_console";
    }

    @RequestMapping("query_dsl")
    public String queryDsl(){
        return "query_dsl";
    }

}
