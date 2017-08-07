/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import la.kaike.ksearch.biz.dal.mapper.RoleMapper;
import la.kaike.ksearch.biz.dal.mapper.UserMapper;
import la.kaike.ksearch.biz.service.UserService;
import la.kaike.ksearch.model.dbo.user.Role;
import la.kaike.ksearch.model.dbo.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月13日 下午2:52 $
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public User getUserById(String userId) {
        User user = userMapper.selectById(userId);

        return user;
    }

    @Override
    public Role getRoleById(Integer roleId) {

        return roleMapper.selectById(roleId);
    }

    @Override
    public User queryUser(User user) {
        User ret = userMapper.selectOne(user);
        return ret;
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.setEntity(user);
        userMapper.update(user,entityWrapper);
    }
}
