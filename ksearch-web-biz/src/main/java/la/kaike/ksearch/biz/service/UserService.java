/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import java.util.List;
import java.util.Map;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月13日 下午2:50 $
 */
public interface UserService {


    /**
     * 根据条件查询用户列表
     * @param name
     * @param beginTime
     * @param endTime
     * @param deptid
     * @return
     */
    List<Map<String, Object>> selectUsers(String name, String beginTime,String endTime,Integer deptid);
}
