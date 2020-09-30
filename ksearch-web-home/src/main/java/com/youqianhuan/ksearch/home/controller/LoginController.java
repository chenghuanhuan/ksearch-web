/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.controller;

import com.youqianhuan.ksearch.biz.service.UserService;
import com.youqianhuan.ksearch.model.Response;
import com.youqianhuan.ksearch.model.vo.login.LoginReqVO;
import com.youqianhuan.ksearch.util.util.MD5Util;
import com.youqianhuan.ksearch.home.base.BaseController;
import com.youqianhuan.ksearch.model.dbo.user.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**import com.youqianhuan.ksearch.util.constant.WebConstant;
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午1:36 $
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping
    public String index(){
        return "login";
    }

    /**
     * 登录
     * @param loginReqVO
     * @return
     */
    @RequestMapping("/do")
    @ResponseBody()
    public Response login(LoginReqVO loginReqVO){

        return succeed();
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        return "login";
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody()
    public Response user(){

        return succeed(new User());
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encrypt("123456"));
    }
}
