/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.dbo.user.Role;
import la.kaike.ksearch.model.dbo.user.User;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月13日 下午2:50 $
 */
public interface UserService{
    /**
     * 通过id获取用户信息
     * @param userId
     * @return
     */
    User getUserById(String userId);

    /**
     * 获取角色信息
     * @param roleId
     * @return
     */
    Role getRoleById(Integer roleId);

    /**
     * 获取用户
     * @param user
     * @return
     */
    User queryUser(User user);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);
}
