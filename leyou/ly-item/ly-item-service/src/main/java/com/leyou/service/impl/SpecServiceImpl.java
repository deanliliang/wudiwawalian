package com.leyou.service.impl;

import com.leyou.bean.SpecGroup;
import com.leyou.bean.SpecParam;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mappers.SpecGroupMapper;
import com.leyou.mappers.SpecParamMapper;
import com.leyou.service.SpecService;
import com.leyou.utils.BeanHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
public class SpecServiceImpl implements SpecService {

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
//        List<SpecParam> specParamsList = new ArrayList<>();

        for (SpecGroupDTO specGroupDTO : specGroupDTOS) {
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(specGroupDTO.getId());
//            先根据id查询出所有spec集合
            List<SpecParam> specParamList = specParamMapper.select(specParam);
//            根据id查询到的spec集合放入specGroupDTO中
            specGroupDTO.setParams(BeanHelper.copyWithCollection(specParamList,SpecParamDTO.class));
        }

        return specGroupDTOS;
    }

    /**
     * 查询分组参数信息
     *
     * @param gid
     * @param cid
     * @param searching
     * @return
     */
    @Override
    public List<SpecParamDTO> querySpecParamsById(Long gid, Long cid, Boolean searching) {
        // gid和cid必选一个
        if (gid == null && cid == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setSearching(searching);
        specParam.setCid(cid);

        //根据gid查询分组参数
        List<SpecParam> specParamList = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(specParamList)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        //转换为DTO对象list
        return BeanHelper.copyWithCollection(specParamList, SpecParamDTO.class);

    }
}
