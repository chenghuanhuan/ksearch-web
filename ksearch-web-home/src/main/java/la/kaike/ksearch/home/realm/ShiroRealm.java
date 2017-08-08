/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.realm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import la.kaike.ksearch.biz.service.UserService;
import la.kaike.ksearch.biz.service.RoleService;
import la.kaike.ksearch.model.dbo.user.Role;
import la.kaike.ksearch.model.dbo.user.User;
import la.kaike.ksearch.util.constant.WebConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类从principals中取得用户名称进行匹配,在principals中默认保存了当前登陆人的用户名称,从而将该用户的角色加入到作用域中;
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 上午10:31 $
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;


    /**
     * 登陆第二步,通过用户信息将其权限和角色加入作用域中,达到验证的功能
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 根据用户配置用户与权限
        if (principals == null) {
            throw new AuthorizationException(
                    "PrincipalCollection method argument cannot be null.");
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USER_KEY);
        String userId = (String) getAvailablePrincipal(principals);
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        // 通过当前登陆用户的姓名查找到相应的用户的所有信息
        //User user = userService.getUserById(userId);
        if (user.getUserId().equals(userId)) {
            Role role = roleService.selectById(user.getRoleId());
            if (role!=null) {
                // 装配用户的角色和权限 delete
                roles.add(role.getRoleId().toString());
                String permissionsStr = role.getPermissions();
                String[] arrPermission = permissionsStr.split(",");
                for (String permission:arrPermission) {
                    permissions.add(permission);
                }
                user.setRole(role);
            }
        } else {
            throw new AuthorizationException();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 为用户设置角色和权限
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 验证当前登录的Subject
     * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        // 登陆后的操作,此处为登陆有的第一步操作,从LoginController.login中调用了此处的token
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        System.out.println("token is :" + token);
        // 简单默认一个用户,实际项目应User user = userService.getByAccount(token.getUsername());
        // 下面通过读取token中的数据重新封装了一个user
        String pwd = new String(token.getPassword());
        User user = new User();
        user.setPassword(pwd);
        user.setUserId(token.getUsername());
        User dbUser = userService.selectOne(new EntityWrapper<>(user));
        if (dbUser == null) {
            throw new AuthorizationException();
        }
        SimpleAuthenticationInfo info = null;
        if (dbUser.getUserId().equals(token.getUsername())) {
            info = new SimpleAuthenticationInfo(dbUser.getUserId(),
                    dbUser.getPassword(), getName());
            //将该User村放入session作用域中
            //dbUser.setPassword(null);
            this.setSession(WebConstant.SESSION_USER_KEY, dbUser);
        }

        return info;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    @SuppressWarnings("unused")
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out
                    .println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
