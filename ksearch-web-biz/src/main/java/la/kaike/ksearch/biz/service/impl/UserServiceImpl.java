/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import la.kaike.ksearch.biz.dal.mapper.UserMapper;
import la.kaike.ksearch.biz.service.UserService;
import la.kaike.ksearch.model.dbo.user.User;
import org.springframework.stereotype.Service;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午6:14 $
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
