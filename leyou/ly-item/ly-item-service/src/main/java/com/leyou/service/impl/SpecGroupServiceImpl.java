package com.leyou.service.impl;

import com.leyou.bean.SpecGroup;
import com.leyou.bean.SpecParam;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.mappers.SpecGroupMapper;
import com.leyou.mappers.SpecParamMapper;
import com.leyou.service.SpecGroupService;
import com.leyou.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service.impl
 * @ClassName: SpecGroupService
 * @Author: Dean
 * @Description: SpecGroup操作
 * @Date: 2019/6/19 1:26
 * @Version: 1.0
 */
@Service
public class SpecGroupServiceImpl implements SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 查询分组
     *
     * @param id
     * @return
     */
    @Override
    public List<SpecGroupDTO> querySpecGroupById(Long id) {
        SpecGroup specGroup = new SpecGroup();
        //根据cid查询分组数据
        specGroup.setCid(id);
        List<SpecGroup> specGroupList = specGroupMapper.select(specGroup);
//        转换为DTO对象list
        List<SpecGroupDTO> specGroupDTOS = BeanHelper.copyWithCollection(specGroupList, SpecGroupDTO.class);
        return specGroupDTOS;
    }

    /**
     * 查询分组参数信息
     *
     * @param gid
     * @return
     */
    @Override
    public List<SpecParamDTO> querySpecParamsById(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
//根据gid查询分组参数
        List<SpecParam> specParamList = specParamMapper.select(specParam);
        //        转换为DTO对象list
        List<SpecParamDTO> specParamDTOS = BeanHelper.copyWithCollection(specParamList, SpecParamDTO.class);

        return specParamDTOS;
    }
}
