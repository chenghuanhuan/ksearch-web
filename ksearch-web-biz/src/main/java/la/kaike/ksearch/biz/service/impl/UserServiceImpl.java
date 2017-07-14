/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service.impl;

import la.kaike.ksearch.biz.dal.mapper.UserMapper;
import la.kaike.ksearch.biz.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月13日 下午2:52 $
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Map<String, Object>> selectUsers(String name, String beginTime, String endTime, Integer deptid) {
        return userMapper.selectUsers(name,beginTime,endTime,deptid);
    }
}
