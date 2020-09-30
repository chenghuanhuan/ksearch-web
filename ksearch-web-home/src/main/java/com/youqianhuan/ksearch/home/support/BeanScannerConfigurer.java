/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * BeanScannerConfigurer用于嵌入到Spring的加载过程的中，这里用到了BeanFactoryPostProcessor 和 ApplicationContextAware。
 * Spring提供了一些的接口使程序可以嵌入Spring的加载过程。这个类中的继承ApplicationContextAware接口，
 * Spring会读取ApplicationContextAware类型的的JavaBean，
 * 并调用setApplicationContext(ApplicationContext applicationContext)传入Spring的applicationContext。
 * 同样继承BeanFactoryPostProcessor接口，Spring会在BeanFactory的相关处理完成后调用postProcessBeanFactory方法，进行定制的功能。
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2018年01月29日 下午3:12 $
 */
@Component
public class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(BeanScannerConfigurer.class);
    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
        scanner.setResourceLoader(this.applicationContext);
        scanner.scan("com.youqianhuan.ksearch.model.vo");
        log.info("---------------------------------------初始化ES查询bean结束--------------------------");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
    }
}
