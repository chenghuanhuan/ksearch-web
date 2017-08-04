/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.query;

import la.kaike.ksearch.model.vo.PageVO;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月28日 上午9:49 $
 */
public class SimpleQueryReqVO extends PageVO {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 索引
     */
    private List<String> indices;

    /**
     * 类型
     */
    private List<String> types;

    /**
     * 排序字段
     */
    private List<SortFieldVO> sorts;

    private String sort;

    private String order;

    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<SortFieldVO> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortFieldVO> sorts) {
        this.sorts = sorts;
    }

    public List<String> getIndices() {
        return indices;
    }

    public void setIndices(List<String> indices) {
        this.indices = indices;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
