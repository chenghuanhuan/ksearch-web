/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.nginx.NginxAccessLogVO;
import la.kaike.ksearch.model.vo.nginx.NginxErrorLogVO;

import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年10月25日 下午3:11 $
 */
public interface NginxLogService {
    /**
     * 错误日志列表查询
     * @param errorLogVO
     * @return
     */
    PageResponse errorList(NginxErrorLogVO errorLogVO) throws ParseException;


    /**
     * 访问日志
     * @param accessLogVO
     * @return
     */
    PageResponse accessList(NginxAccessLogVO accessLogVO) throws ParseException;
}
