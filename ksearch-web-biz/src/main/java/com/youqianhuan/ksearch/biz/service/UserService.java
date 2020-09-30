/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.youqianhuan.ksearch.model.dbo.user.User;
import com.youqianhuan.ksearch.model.vo.user.UserPageReqVO;

import java.util.List;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午5:55 $
 */
public interface UserService extends IService<User> {
    List<User> selectListPage(UserPageReqVO userPageReqVO);

    void testLog();
    void testLog2();
}
