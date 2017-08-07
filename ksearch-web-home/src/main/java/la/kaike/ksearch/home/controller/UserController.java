/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import com.baomidou.mybatisplus.plugins.Page;
import la.kaike.ksearch.biz.service.IUserService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.dbo.user.User;
import la.kaike.ksearch.model.vo.user.UserPageReqVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午2:01 $
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Resource
    private IUserService userService;

    @RequestMapping
    public String index(){
        return "user_manager";
    }

    /**
     * 获取用户列表
     * @param userPageReqVO
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody()
    public Response user(UserPageReqVO userPageReqVO){

        Page<User> query = new Page<>();
        query.setSize(userPageReqVO.getLimit());
        query.setSearchCount(true);
        query.setCurrent(userPageReqVO.getOffset());
        Page<User> userPage = userService.selectPage(query);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setRows(userPage.getRecords());
        pageResponse.setTotal(userPage.getTotal());
        return succeed(pageResponse);
    }

}
