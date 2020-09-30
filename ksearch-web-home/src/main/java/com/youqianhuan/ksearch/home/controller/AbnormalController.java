/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenghuanhuan@youqianhuan.com
 * @since $Revision:1.0.0, $Date: 2017年07月14日 下午2:01 $
 */
@Controller
@RequestMapping("/abnormal")
public class AbnormalController {
    @RequestMapping
    public String index(){
        return "abnormal";
    }
}