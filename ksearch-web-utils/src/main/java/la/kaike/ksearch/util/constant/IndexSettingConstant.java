/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.util.constant;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月18日 下午4:06 $
 */
public interface IndexSettingConstant {

    //"index.number_of_shards" -> "5"
    //"index.number_of_replicas" -> "1"
    //"index.creation_date" -> "1498813262911"
    //"index.provided_name" -> "website"
    //"index.uuid" -> "6BUJ_EefQXa6VYRrIQKBGQ"
    //"index.version.created" -> "5040399"
    /**
     * 主分片个数
     */
    String NUMBER_OF_SHARDS = "index.number_of_shards";

    /**
     * 副本分片个数
     */
    String NUMBER_OF_REPLICAS = "index.number_of_replicas";
}
