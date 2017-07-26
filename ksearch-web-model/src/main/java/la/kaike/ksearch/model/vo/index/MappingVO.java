/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.index;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月25日 上午11:19 $
 */
public class MappingVO {

    private String type;

    private List<PropertiesVO> properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropertiesVO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesVO> properties) {
        this.properties = properties;
    }
}
