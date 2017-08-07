/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import la.kaike.ksearch.biz.dal.mapper.RoleMapper;
import la.kaike.ksearch.biz.service.RoleService;
import la.kaike.ksearch.model.dbo.user.Role;
import org.springframework.stereotype.Service;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午7:22 $
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
