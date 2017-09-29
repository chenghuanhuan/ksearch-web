/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.applog;

import la.kaike.ksearch.model.ClusterRequest;
import la.kaike.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年09月29日 上午10:15 $
 */
public class AppLogIdVO extends ClusterRequest {
    /**
     * 版本号
     */
    @Validate(maxLength = 1024)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
