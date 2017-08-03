/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.query;

import la.kaike.ksearch.model.ClusterRequest;
import la.kaike.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月03日 上午10:36 $
 */
public class ExecuteReqVO extends ClusterRequest{

    @Validate(isNotBlank = true)
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
