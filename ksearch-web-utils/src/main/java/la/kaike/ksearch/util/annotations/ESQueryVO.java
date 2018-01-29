/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.util.annotations;

import java.lang.annotation.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月29日 上午10:51 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface ESQueryVO {
    /**
     * 索引
     * @return
     */
    String index();

    /**
     * 类型
     * @return
     */
    String type();
}
