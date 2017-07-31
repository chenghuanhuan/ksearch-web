/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.home.controller;

import la.kaike.ksearch.biz.service.ElasticSearchService;
import la.kaike.ksearch.home.base.BaseController;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.vo.SelectVO;
import la.kaike.ksearch.model.vo.elastic.IndicesVO;
import la.kaike.ksearch.model.vo.index.GetMappingReqVO;
import la.kaike.ksearch.model.vo.index.MappingVO;
import la.kaike.ksearch.model.vo.index.PropertiesVO;
import la.kaike.ksearch.model.vo.query.SimpleQueryReqVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月31日 下午2:17 $
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Resource
    private ElasticSearchService elasticSearchService;

    /**
     * 索引下拉
     * @param simpleQueryReqVO
     * @return
     */
    @RequestMapping("/index/select")
    @ResponseBody
    public Response indexSelect(SimpleQueryReqVO simpleQueryReqVO){
        List<IndicesVO> indicesVOS =  elasticSearchService.getIndicesVO("");
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

    @RequestMapping("/fields")
    @ResponseBody
    public Response fields(GetMappingReqVO reqVO){
        List<MappingVO> mappingVOList =  elasticSearchService.getAllMapping(reqVO);
        List<String> fields = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mappingVOList)){
            for (MappingVO mappingVO:mappingVOList){
                List<PropertiesVO> propertiesVOList = mappingVO.getProperties();
                for (PropertiesVO propertiesVO:propertiesVOList){
                    fields.add(propertiesVO.getName());
                }
            }
        }
        return succeed(fields);
    }

}
