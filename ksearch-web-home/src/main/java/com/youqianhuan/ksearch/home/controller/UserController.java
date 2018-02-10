/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.youqianhuan.ksearch.biz.service.RoleService;
import com.youqianhuan.ksearch.biz.service.UserService;
import com.youqianhuan.ksearch.home.base.BaseController;
import com.youqianhuan.ksearch.model.PageResponse;
import com.youqianhuan.ksearch.model.Response;
import com.youqianhuan.ksearch.model.vo.user.ModifyPwdReqVO;
import com.youqianhuan.ksearch.model.vo.user.UserDelReqVO;
import com.youqianhuan.ksearch.model.vo.user.UserSaveReqVO;
import com.youqianhuan.ksearch.util.util.MD5Util;
import com.youqianhuan.ksearch.model.dbo.user.User;
import com.youqianhuan.ksearch.model.vo.user.UserPageReqVO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午2:01 $
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @RequiresRoles({"2"})
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
    @ResponseBody
    @RequiresRoles({"2"})
    public Response user(UserPageReqVO userPageReqVO){

        Page<User> query = new Page<>();
        query.setSize(userPageReqVO.getLimit());
        query.setSearchCount(true);
        query.setCurrent(userPageReqVO.getOffset());
        //Page<User> userPage = userService.selectPage(query, Condition.create().eq("status",1));
        Page<User> userPage = new Page<>();

        User user = new User();
        user.setUserId(userPageReqVO.getUserId());
        user.setUsername(userPageReqVO.getUsername());
        user.setStatus(Integer.valueOf(1));
        int count = userService.selectCount(new EntityWrapper<>(user));
        if (count>0){
            List<User> userList = userService.selectListPage(userPageReqVO);
            userPage.setRecords(userList);
        }
        userPage.setTotal(count);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setRows(userPage.getRecords());
        pageResponse.setTotal(userPage.getTotal());
        return succeed(pageResponse);
    }

    /**
     * 保存
     * @param userSaveReqVO
     * @return
     */
    @RequiresRoles({"2"})
    @RequestMapping("/save")
    @ResponseBody
    public Response save(UserSaveReqVO userSaveReqVO){
        User user = new User();
        BeanUtils.copyProperties(userSaveReqVO,user);
        User operator = getLoginUser();
        if (user.getUserId()!=null) {
            user.setCreateBy(operator.getUserId());
            user.setCreateTime(new Date());
        }
        if (userService.selectById(userSaveReqVO.getUserId())==null) {
            // 设置初始密码
            String pwd = MD5Util.encrypt("123456");
            user.setPassword(pwd);
        }
        user.setUpdateBy(operator.getUserId());
        user.setUpdateTime(new Date());
        userService.insertOrUpdate(user);
        return succeed();
    }

    /**
     * 删除
     * @param delReqVO
     * @return
     */
    @RequiresRoles({"2"})
    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(UserDelReqVO delReqVO){
        User user = new User();
        user.setUserId(delReqVO.getUserId());
        user.setStatus(0);
        userService.insertOrUpdate(user);
        return succeed();
    }

    /**
     * 获取单个用户信息
     * @param delReqVO
     * @return
     */
    @RequiresRoles({"2"})
    @RequestMapping("/get")
    @ResponseBody
    public Response get(UserDelReqVO delReqVO){
        User user = userService.selectById(delReqVO.getUserId());
        if (user!=null) {
            user.setPassword(null);
        }
        return succeed(user);
    }

    /**
     * 修改用户密码
     * @param modifyPwdReqVO
     * @return
     */
    //@RequiresRoles({"2"})
    @RequestMapping("/password")
    @ResponseBody
    public Response password(ModifyPwdReqVO modifyPwdReqVO){
        if (!modifyPwdReqVO.getPassword().equals(modifyPwdReqVO.getSecondPwd())){
            return failed("两次输入的密码不相同！");
        }
        //User user = userService.selectById(modifyPwdReqVO.getUserId());
        User user = this.getLoginUser();
        String currPwd = modifyPwdReqVO.getCurrPwd();
        if (!MD5Util.encrypt(currPwd).equals(user.getPassword())){
            return failed("原密码不正确");
        }
        user.setPassword(MD5Util.encrypt(modifyPwdReqVO.getPassword()));
        userService.update(user,null);
        return succeed("保存成功");
    }
}
