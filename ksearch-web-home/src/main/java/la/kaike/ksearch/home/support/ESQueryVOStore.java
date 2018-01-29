/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.support;

import la.kaike.ksearch.util.annotations.ESQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月29日 下午4:34 $
 */
public class ESQueryVOStore {

    private static final Logger log = LoggerFactory.getLogger(ESQueryVOStore.class);

    private static Map<String,String> classStore = new ConcurrentHashMap<>(1);

    public static void set(String className){
        try {
            Class<?> clazz = Class.forName(className);
            ESQueryVO esQueryVO = clazz.getAnnotation(ESQueryVO.class);
            String key = esQueryVO.index()+"*"+esQueryVO.type();
            classStore.put(key,className);
        } catch (ClassNotFoundException e) {
            log.error("class {} not found!",className,e);
        }
    }

    public static Map<String,String> getClassStore(){
        return classStore;
    }

    public static String getClassName(String index,String type){

        String key = index+"*"+type;

        return classStore.get(key);
    }
}
