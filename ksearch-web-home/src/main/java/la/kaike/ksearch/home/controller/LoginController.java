/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.UserService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.dbo.user.User;
import la.kaike.ksearch.model.vo.login.LoginReqVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author chenghuanhuan@kaike.la
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

        User user = userService.getUserById(loginReqVO.getUserId());
        if (user != null
                && user.getUserId().equals(loginReqVO.getUserId())
                &&user.getPassword().equals(loginReqVO.getPassword())){
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserId(), user
                    .getPassword().toString());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return succeed();
        }

        return failed("用户名或密码错误！");
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){

        SecurityUtils.getSubject().logout();

        return "login";
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody()
    public Response user(){
        User user = getLoginUser();
        return succeed(user);
    }
}
