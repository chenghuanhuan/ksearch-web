/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.syslog.SysLogVO;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年11月16日 下午5:39 $
 */
public interface SysLogService {

    /**
     * 查询日志
     * @param sysLogVO
     * @return
     */
    PageResponse query(SysLogVO sysLogVO);
}
