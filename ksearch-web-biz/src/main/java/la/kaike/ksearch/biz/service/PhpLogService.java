/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.phplog.PhpLogVO;

import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年12月15日 上午11:54 $
 */
public interface PhpLogService {
    /**
     * 查询日志
     * @param phpLogVO
     * @return
     */
    PageResponse query(PhpLogVO phpLogVO) throws ParseException;
}
