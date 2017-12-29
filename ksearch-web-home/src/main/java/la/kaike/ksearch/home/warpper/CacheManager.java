/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.warpper;

import com.google.common.cache.LoadingCache;
import la.kaike.ksearch.model.vo.elastic.IndicesVO;

import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年12月29日 下午6:44 $
 */
public class CacheManager {

    public static LoadingCache<String, List<IndicesVO>> cache = null;

    public static void refresh(String key){
        if (cache!=null){
            cache.refresh(key);
        }
    }
}
