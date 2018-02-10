/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.model.vo.node;

import la.kaike.ksearch.model.validate.Validate;
import la.kaike.ksearch.model.vo.PageVO;
import la.kaike.ksearch.util.annotations.ESQuery;
import la.kaike.ksearch.util.annotations.ESQueryVO;
import la.kaike.ksearchclient.annotations.FieldType;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月31日 上午11:54 $
 */
@ESQueryVO(indexRegular = "^nodejs.log.[A-Za-Z\\-]\\d\\d\\d\\d-\\d\\d",type = "log")
public class NodeLogVO extends PageVO {


    @Validate(required = true,isNotBlank = true,desc = "集群名称")
    private String clusterName;

    /**
     * 类别
     */
    @ESQuery(type = FieldType.keyword,order = 1)
    private String category;
    @ESQuery(type = FieldType.text,order = 2)
    private String context;
    @ESQuery(type = FieldType.keyword,order = 3)
    private String level;

    @ESQuery(type = FieldType.Date,order = -1,sortable = true,format = "yyyy-MM-dd HH:mm:ss.SSS")
    private String datetime;
    @ESQuery(type = FieldType.keyword,order = 3,field = "beat.hostname")
    private String hostname;


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
