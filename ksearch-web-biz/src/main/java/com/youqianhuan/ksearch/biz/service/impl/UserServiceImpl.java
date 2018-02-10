/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youqianhuan.ksearch.biz.dal.mapper.UserMapper;
import com.youqianhuan.ksearch.biz.service.UserService;
import com.youqianhuan.ksearch.model.dbo.user.User;
import com.youqianhuan.ksearch.model.vo.index.OpenIndexReqVO;
import com.youqianhuan.ksearch.model.vo.user.UserPageReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午6:14 $
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public List<User> selectListPage(UserPageReqVO userPageReqVO) {
        return this.baseMapper.selectListPage(userPageReqVO);
    }

    @Override
    public void testLog() {
        try{
            int i = 1/0;
        }catch (Exception e){
            logger.error("出错啦",e);
        }

        try{
            OpenIndexReqVO openIndexReqVO=null;
            openIndexReqVO.getClusterName();
        }catch (Exception e){
            logger.error("出错啦,哈哈哈哈哈哈",e);
        }

        try{
            List list = new ArrayList();
            list.get(10);
        }catch (Exception e){
            logger.error("出错啦,哎哎哎",e);
        }
    }

    @Override
    public void testLog2() {
        logger.error("ahhhhhhhhdsffsfsfsfsf\nfsfsfsfsfsffffffffffffffffff\nsfsffffffffffffffffff");
    }
}
