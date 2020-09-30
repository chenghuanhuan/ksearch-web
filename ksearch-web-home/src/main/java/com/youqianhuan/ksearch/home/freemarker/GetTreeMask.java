/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.freemarker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 
 * @author chenghuanhuan@youqianhuan.com
 * @version $Id: GetTreeMask.java, v 0.1 2016年4月15日 下午5:42:53 user Exp $
 */
@SuppressWarnings("deprecation")
public class GetTreeMask implements TemplateMethodModel {

    private static final Logger logger = LoggerFactory.getLogger(GetTreeMask.class);

    /** 
     * @see freemarker.template.TemplateMethodModel#exec(List)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arg0) throws TemplateModelException {
        try {
            if (!CollectionUtils.isEmpty(arg0)) {
                String arg = (String) arg0.get(0);
                Integer level = Integer.valueOf(arg);
                return generateMask(level);
            }
        } catch (Exception e) {
            logger.error("GetTreeMask error", e);
            //return "";
        }

        return "";
    }

    private String generateMask(Integer i) {
        String a = "";
        int val = 17 + 17 * i;
        if (i > 0) {
            a = "style='padding-left: " + val + "px !important;'";
        }
        return a;
    }

}
