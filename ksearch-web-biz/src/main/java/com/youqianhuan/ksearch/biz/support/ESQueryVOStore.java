/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.support;

import com.youqianhuan.ksearch.util.annotations.ESQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2018年01月29日 下午4:34 $
 */
public class ESQueryVOStore {

    private static final Logger log = LoggerFactory.getLogger(ESQueryVOStore.class);

    private static Map<ESQueryVO,String> classStore = new ConcurrentHashMap<>(1);

    /**
     * 索引与类关联关系缓存
     */
    private static Map<String,String> classStoreCache = new ConcurrentHashMap<>(1);

    public static void set(String className){
        try {
            Class<?> clazz = Class.forName(className);
            ESQueryVO esQueryVO = clazz.getAnnotation(ESQueryVO.class);
            classStore.put(esQueryVO,className);
        } catch (ClassNotFoundException e) {
            log.error("class {} not found!",className,e);
        }
    }

    /**
     * 获取关联的查询条件类
     * @param index
     * @param type
     * @return
     */
    public static String getClassName(String index,String type){

        String keyCacheKey = index+"_"+type;
        String className = classStoreCache.get(keyCacheKey);
        if (className==null) {
            for (Map.Entry<ESQueryVO, String> set : classStore.entrySet()) {
                String key = set.getKey().indexRegular();
                Pattern pattern = Pattern.compile(key);
                Matcher matcher = pattern.matcher(index);
                if (matcher.matches() && type.equals(set.getKey().type())) {
                    className = set.getValue();
                    classStoreCache.put(keyCacheKey, className);
                    break;
                }
            }
        }

        return className;
    }
}
