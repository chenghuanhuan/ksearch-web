/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.util.annotations;

import la.kaike.ksearchclient.annotations.FieldType;

import java.lang.annotation.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月12日 下午5:47 $
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ESQuery {

    FieldType type() default FieldType.keyword;

    /**
     * 格式化格式
     * @return
     */
    String format() default "yyyy/MM/dd HH:mm:ss";

    /**
     * 是否忽略查询
     * @return
     */
    boolean ignore() default true;

    /**
     * 映射的字段名称
     * @return
     */
    String field() default "";


    /**
     * 是否在界面显示
     * @return
     */
    boolean show() default true;

    /**
     * 排序顺序
     * @return
     */
    int order() default 0;

}
