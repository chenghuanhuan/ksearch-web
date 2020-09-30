/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.task;

import com.youqianhuan.ksearch.biz.service.ElasticSearchService;
import com.youqianhuan.ksearch.biz.support.CustomPropertyPlaceholderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年10月24日 上午11:08 $
 */
@Component("indexTask")
public class IndexTask {

    private final static Logger logger = LoggerFactory.getLogger(IndexTask.class);

    @Resource
    private ElasticSearchService elasticSearchService;

    @Resource
    private CustomPropertyPlaceholderConfigurer configurer;

    //每天凌晨2:00执行
    @Scheduled(cron = "0 00 2 * * ?")
    //@Scheduled(cron = "10 * * * * ?")
    public void TestTask() {

        String clusterNames = configurer.getProperty("ksearch.cluster.name");
        String [] clusterNameArr = clusterNames.split(",");
        for (int i=0;i<clusterNameArr.length;i++) {
            try {
                elasticSearchService.clearOldIndex(clusterNameArr[i]);
            } catch (ParseException e) {
                logger.error("删除索引任务失败,clusterName={}",clusterNameArr[i],e);
            }
        }
    }
}
