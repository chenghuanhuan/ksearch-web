/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.task;

import com.baidu.disconf.client.usertools.IKuKoConfDataGetter;
import la.kaike.ksearch.biz.service.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年10月24日 上午11:08 $
 */
@Component("indexTask")
public class IndexTask {

    private final static Logger logger = LoggerFactory.getLogger(IndexTask.class);

    @Resource
    private ElasticSearchService elasticSearchService;

    //每天凌晨1:00执行
    //@Scheduled(cron = "0 00 1 * * ?")
    @Scheduled(cron = "10 * * * * ?")
    public void TestTask() {

        String clusterNames = IKuKoConfDataGetter.getStringValue("ksearch.cluster.name");
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
