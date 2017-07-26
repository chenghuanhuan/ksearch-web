/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.index;

import la.kaike.ksearch.model.Request;
import la.kaike.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月25日 上午11:13 $
 */
public class GetMappingVO extends Request {
    @Validate(required = true)
    private String clusterName;
    @Validate(required = true,isNotBlank = true,desc = "索引名称")
    private String index;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
