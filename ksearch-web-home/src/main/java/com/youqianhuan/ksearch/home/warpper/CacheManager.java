/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.warpper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.youqianhuan.ksearch.biz.service.ElasticSearchService;
import com.youqianhuan.ksearch.util.exception.BizExceptionEnum;
import com.youqianhuan.ksearch.util.exception.BussinessException;
import com.youqianhuan.ksearch.model.vo.elastic.IndicesVO;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年12月29日 下午6:44 $
 */
public class CacheManager {

    public static LoadingCache<String, List<IndicesVO>> cache = null;

    public static void refresh(String key){
        if (cache!=null){
            cache.refresh(key);
        }
    }

    public static List<IndicesVO> get(String key){
        try {
            return CacheManager.cache.get(key);
        } catch (ExecutionException e) {
            throw new BussinessException(BizExceptionEnum.CACHE_EXCEPTION);
        }
    }

    public synchronized static void init(ElasticSearchService elasticSearchService){
        CacheManager.cache = CacheBuilder.newBuilder()
                //设置cache的初始大小为10，要合理设置该值
                .initialCapacity(10)
                //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                .concurrencyLevel(10)
                //设置cache中的数据在写入之后的存活时间为3秒
                .expireAfterWrite(1, TimeUnit.HOURS)
                //构建cache实例
                .build(new CacheLoader<String, List<IndicesVO>>() {
                    @Override
                    public List<IndicesVO> load(String key) throws Exception {
                        String clusterName = key.replace("_indeices","");
                        List<IndicesVO> indicesVOList = elasticSearchService.getIndicesVO(clusterName);
                        return indicesVOList;
                    }
                });
    }
}
