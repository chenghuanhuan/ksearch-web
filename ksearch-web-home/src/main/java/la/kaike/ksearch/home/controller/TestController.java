/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.UserService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.home.warpper.UserWarpper;
import la.kaike.ksearch.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月13日 上午11:05 $
 */

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        List<Map<String, Object>> users = null;
        return new UserWarpper(users).warp();
    }

    @RequestMapping("/index")
    public String index(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("/index");
        //total 是模板的全局变量，可以直接访问
        //view.addObject("total",service.getCount());
        return view;
    }

    @RequestMapping(value = "/logtest", method = RequestMethod.GET)
    @ResponseBody
    public Response validate() {


        userService.testLog();

        return succeed();
    }
    @RequestMapping(value = "/logtest2", method = RequestMethod.GET)
    @ResponseBody
    public Response logtest2() {


        userService.testLog2();

        return succeed();
    }
}
