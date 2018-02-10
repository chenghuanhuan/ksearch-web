/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youqianhuan.ksearch.biz.dal.mapper.RoleMapper;
import com.youqianhuan.ksearch.biz.service.RoleService;
import com.youqianhuan.ksearch.model.dbo.user.Role;
import org.springframework.stereotype.Service;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午7:22 $
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
