/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import com.baomidou.mybatisplus.service.IService;
import la.kaike.ksearch.model.dbo.user.User;
import la.kaike.ksearch.model.vo.user.UserPageReqVO;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月07日 下午5:55 $
 */
public interface UserService extends IService<User> {
    List<User> selectListPage(UserPageReqVO userPageReqVO);
}
