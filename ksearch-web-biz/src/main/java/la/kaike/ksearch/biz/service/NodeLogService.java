/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.service;

import la.kaike.ksearch.model.PageResponse;
import la.kaike.ksearch.model.vo.node.NodeLogVO;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月31日 下午12:02 $
 */
public interface NodeLogService {
    PageResponse query(NodeLogVO nodeLogVO);
}
