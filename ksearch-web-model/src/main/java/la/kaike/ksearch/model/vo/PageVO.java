/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo;

import la.kaike.ksearch.model.ClusterRequest;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月28日 下午3:05 $
 */
public class PageVO extends ClusterRequest {
    /**
     * 第多少条开始
     */
    private int offset;

    /**
     * 每页显示个数
     */
    private int limit;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
