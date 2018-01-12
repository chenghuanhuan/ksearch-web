/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.hotfix.HotFixVO;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月12日 下午5:42 $
 */
public interface HotFixService {

    PageResponse query(HotFixVO hotFixVO);

}
