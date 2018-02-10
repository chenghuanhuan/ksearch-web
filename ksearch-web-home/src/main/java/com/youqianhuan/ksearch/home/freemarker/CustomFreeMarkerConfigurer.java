/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.freemarker;

import freemarker.cache.TemplateLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;

/**
 * 自定义的FreeMarkerConfigurer
 * @author chenghuanhuan@kaike.la
 * @version $Id: CustomFreeMarkerConfigurer.java, v 0.1 2016年5月4日 上午9:38:15 user Exp $
 */
public class CustomFreeMarkerConfigurer extends FreeMarkerConfigurer {
    @Override
    protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {

        return new HtmlTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));

    }
}
