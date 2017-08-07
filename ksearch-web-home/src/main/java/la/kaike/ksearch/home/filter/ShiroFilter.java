/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.filter;

import la.kaike.ksearch.biz.service.UserService;
import la.kaike.ksearch.model.dbo.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 上午10:27 $
 */
public class ShiroFilter implements Filter {

    @Resource
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("启用filter........");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Principal principal = httpRequest.getUserPrincipal();
        System.out.println(principal);
        System.out.println("getUserPrincipal is :" + principal);
        if (principal != null) {
            Subject subjects = SecurityUtils.getSubject();
            // 为了简单，这里初始化一个用户。实际项目项目中应该去数据库里通过名字取用户：
            // 例如：User user = userService.getByAccount(principal.getName());
            User user = userService.getUserById(principal.getName());
            if (user.getUsername().equals(principal.getName())) {
                UsernamePasswordToken token = new UsernamePasswordToken(
                        user.getUsername(), user.getPassword());
                subjects = SecurityUtils.getSubject();
                subjects.login(token);
                subjects.getSession();
            } else {
                // 如果用户为空，则subjects信息登出
                if (subjects != null) {
                    subjects.logout();
                }
            }
            chain.doFilter(httpRequest, httpResponse);
        }else {
            // TODO 判断请求是否是ajax

            ((HttpServletResponse) response).sendRedirect("/login");
        }


    }

    @Override
    public void destroy() {

    }
}
