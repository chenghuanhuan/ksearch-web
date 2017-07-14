/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.test;

import la.kaike.ksearch.model.Request;
import la.kaike.ksearch.model.validate.Validate;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月14日 上午11:47 $
 */
public class TestVO extends Request{

    @Validate(required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
