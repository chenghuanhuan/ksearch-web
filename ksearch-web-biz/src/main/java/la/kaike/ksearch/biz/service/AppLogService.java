/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.bo.applog.AppLogBO;
import la.kaike.ksearch.model.vo.applog.AppLogIdVO;
import la.kaike.ksearch.model.vo.applog.AppLogVO;

import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月28日 下午3:57 $
 */
public interface AppLogService {

    /**
     * 查询日志
     * @param appLogVO
     * @return
     */
    PageResponse query(AppLogVO appLogVO) throws ParseException;

    /**
     * 通过id获取
     * @param appLogIdVO
     * @return
     */
    AppLogBO queryById(AppLogIdVO appLogIdVO) throws Exception;
}
