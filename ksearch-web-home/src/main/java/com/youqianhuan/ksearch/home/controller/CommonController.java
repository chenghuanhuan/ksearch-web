/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.controller;

import com.youqianhuan.ksearch.biz.service.ElasticSearchService;
import com.youqianhuan.ksearch.biz.service.RoleService;
import com.youqianhuan.ksearch.biz.support.ESQueryVOStore;
import com.youqianhuan.ksearch.home.base.BaseController;
import com.youqianhuan.ksearch.biz.support.CustomPropertyPlaceholderConfigurer;
import com.youqianhuan.ksearch.model.Response;
import com.youqianhuan.ksearch.model.bo.common.QueryConditionBO;
import com.youqianhuan.ksearch.model.dbo.user.Role;
import com.youqianhuan.ksearch.model.vo.SelectVO;
import com.youqianhuan.ksearch.model.vo.common.FieldsVO;
import com.youqianhuan.ksearch.model.vo.common.QueryConditionVO;
import com.youqianhuan.ksearch.model.vo.elastic.IndicesVO;
import com.youqianhuan.ksearch.model.vo.index.GetMappingReqVO;
import com.youqianhuan.ksearch.model.vo.index.MappingVO;
import com.youqianhuan.ksearch.model.vo.index.PropertiesVO;
import com.youqianhuan.ksearch.model.vo.query.SimpleQueryReqVO;
import com.youqianhuan.ksearch.util.annotations.ESQuery;
import com.youqianhuan.ksearch.util.util.ClassUtils;
import com.youqianhuan.ksearch.util.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月31日 下午2:17 $
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Resource
    private ElasticSearchService elasticSearchService;

    @Resource
    private RoleService roleService;

    @Resource
    private CustomPropertyPlaceholderConfigurer configurer;
    /**
     * 索引下拉
     * @param simpleQueryReqVO
     * @return
     */
    @RequestMapping("/index/select")
    @ResponseBody
    public Response indexSelect(SimpleQueryReqVO simpleQueryReqVO){
        // TODO 添加缓存

        List<IndicesVO> indicesVOS =  elasticSearchService.getIndicesVO(simpleQueryReqVO.getClusterName());
        List<SelectVO> selectVOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(indicesVOS)){
            for (IndicesVO indicesVO:indicesVOS){
                SelectVO selectVO = new SelectVO();
                selectVO.setId(indicesVO.getIndex());
                selectVO.setText(indicesVO.getIndex());
                selectVOList.add(selectVO);
            }
        }
        return succeed(selectVOList);
    }

    /**
     * 类型下拉
     * @param reqVO
     * @return
     */
    @RequestMapping("/type/select")
    @ResponseBody
    public Response typeSelect(GetMappingReqVO reqVO){
        List<MappingVO> mappingVOList =  elasticSearchService.getAllMapping(reqVO);
        List<SelectVO> selectVOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mappingVOList)){
            for (MappingVO mappingVO:mappingVOList){
                SelectVO selectVO = new SelectVO();
                selectVO.setId(mappingVO.getType());
                selectVO.setText(mappingVO.getType());
                selectVOList.add(selectVO);
            }
        }
        return succeed(selectVOList);
    }

    @RequestMapping("/role/select")
    @ResponseBody
    public Response roleSelect(){
        List<SelectVO> selectVOList = new ArrayList<>();
        List<Role> roleList = roleService.selectList(null);
        if(CollectionUtils.isNotEmpty(roleList)){
            for (Role role:roleList){
                SelectVO selectVO = new SelectVO();
                selectVO.setId(role.getRoleId().toString());
                selectVO.setText(role.getRoleName());
                selectVOList.add(selectVO);
            }
        }
        return succeed(selectVOList);
    }


    @RequestMapping("/fields")
    @ResponseBody
    public Response fields(GetMappingReqVO reqVO){
        List<MappingVO> mappingVOList =  elasticSearchService.getAllMapping(reqVO);
        List<FieldsVO> fields = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mappingVOList)){
            for (MappingVO mappingVO:mappingVOList){
                fields = converFieldList(mappingVO.getProperties());
            }
        }
        return succeed(fields);
    }

    @RequestMapping("/cleanIndex")
    @ResponseBody
    public Response cleanIndex(){

        String clusterNames = configurer.getProperty("ksearch.cluster.name");
        String [] clusterNameArr = clusterNames.split(",");
        for (int i=0;i<clusterNameArr.length;i++) {
            try {
                elasticSearchService.clearOldIndex(clusterNameArr[i]);
            } catch (ParseException e) {
                logger.error("删除索引任务失败,clusterName={}",clusterNameArr[i],e);
            }
        }
        return succeed();
    }


    /**
     * 获取查询条件
     * @return
     */
    @RequestMapping("/getQueryCondition")
    @ResponseBody
    public Response getQueryCondition(QueryConditionVO query) throws ClassNotFoundException {

        String className = query.getConditionKey();
        if (StringUtils.isEmpty(className)){
            className = ESQueryVOStore.getClassName(query.getIndex(),query.getType());
        }
        Class<?> clazz = Class.forName(className);
        Set<Field> fieldList = ClassUtils.getAllFiled(clazz);
        List<QueryConditionBO> conditionBOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fieldList)){
            for (Field field:fieldList){
                ESQuery esQuery = field.getAnnotation(ESQuery.class);
                if (esQuery!=null){
                    QueryConditionBO queryConditionBO = new QueryConditionBO();
                    queryConditionBO.setField(field.getName());
                    queryConditionBO.setDbField(esQuery.field());
                    queryConditionBO.setFormat(esQuery.format());
                    queryConditionBO.setType(esQuery.type());
                    queryConditionBO.setOrder(esQuery.order());
                    queryConditionBO.setShow(esQuery.show());
                    queryConditionBO.setSortable(esQuery.sortable());
                    conditionBOList.add(queryConditionBO);
                }
            }
        }

        Collections.sort(conditionBOList, Comparator.comparingInt(QueryConditionBO::getOrder));
        return succeed(conditionBOList);
    }




    private List<FieldsVO> converFieldList(List<PropertiesVO> propertiesVOList){
        List<FieldsVO> fieldsVOList = new ArrayList<>();
        for (PropertiesVO propertiesVO:propertiesVOList){
            FieldsVO fieldsVO = new FieldsVO();
            fieldsVO.setFieldName(propertiesVO.getName());
            fieldsVO.setType(propertiesVO.getType());
            fieldsVOList.add(fieldsVO);
            if (propertiesVO.getType()!=null&&(propertiesVO.getType().equals("object")||propertiesVO.getType().equals("nested"))){
                List<FieldsVO> children = converFieldList(propertiesVO.getChildren());
                fieldsVO.setChildren(children);
            }
        }
        return fieldsVOList;
    }
}
