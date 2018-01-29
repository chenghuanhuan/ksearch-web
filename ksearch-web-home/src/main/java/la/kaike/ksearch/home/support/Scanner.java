/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.support;

import la.kaike.ksearch.util.annotations.ESQueryVO;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * Scanner继承的ClassPathBeanDefinitionScanner是Spring内置的Bean定义的扫描器。
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2018年01月29日 下午3:19 $
 */
public class Scanner extends ClassPathBeanDefinitionScanner {
    public Scanner(BeanDefinitionRegistry registry) {
        super(registry);
    }
    public void registerDefaultFilters() {
        //includeFilter里定义了类的过滤器，
        // newAnnotationTypeFilter 表示只取被ESQueryVO.class修饰的类
        this.addIncludeFilter(new AnnotationTypeFilter(ESQueryVO.class));
    }
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions =   super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            //definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
            //definition.setBeanClass(FactoryBean.class);
            ESQueryVOStore.set(definition.getBeanClassName());
        }
        return null;
    }
    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata()
                .hasAnnotation(ESQueryVO.class.getName());
    }
}
