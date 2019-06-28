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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

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
            specGroupDTO.setParams(BeanHelper.copyWithCollection(specParamList, SpecParamDTO.class));
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

    /**
     * 保存spec参数
     *
     * @param map
     */
    @Override
    public void addSpec(Map<String, String> map) {

        if (map.isEmpty()) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        SpecGroup specGroup = new SpecGroup();
//        取出cid和name值
        String name = map.get("name");
        String cid = map.get("cid");
        long l = Long.parseLong(cid);
//        设置相关值
        specGroup.setCid(l);
        specGroup.setName(name);
//        存书数据库
        int insert = specGroupMapper.insert(specGroup);
        if (insert != 1) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpecGroupByid(Long id) {
//        先根据gid删除SpecParam
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(id);
//        根据gid查出中记录数
        int count = specParamMapper.selectCount(specParam);
        int delete = specParamMapper.delete(specParam);
//        两者不想等则删除异常
        if (count!=delete){
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
//        再删除SpecGroup
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        新建对象插入id值
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
//        根据主键删除
        int i = specGroupMapper.deleteByPrimaryKey(specGroup);
        if (i != 1) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
    }

    /**
     * 更新参数
     *
     * @param map
     */
    @Override
    public void updateSpecGroup(Map<String, Object> map) {
//        判断map是否为空
        if (map.isEmpty()) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        取出id 和 name值
        Object o = map.get("id");
        long id = Long.parseLong(o.toString());
        String name = map.get("name").toString();
//        新建对象并且赋值
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        specGroup.setName(name);
//        按照主键id更新
        int i = specGroupMapper.updateByPrimaryKeySelective(specGroup);
        if (i != 1) {
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }
    }

    /**
     * 保存新specparam
     *
     * @param specParam
     */
    @Override
    public void addSpecParam(SpecParam specParam) {
        specParam.setCreateTime(null);
        specParam.setUpdateTime(null);
        if (specParam == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        保存数据
        int insert = specParamMapper.insert(specParam);
        if (insert != 1) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 删除specparam
     * @param id
     */
    @Override
    public void deleteSpecParam(Long id) {
        if (id==null){
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
//        删除数据
        int delete = specParamMapper.delete(specParam);
        if (delete!=1){
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
    }

    /**
     * 更新specPaeam
     * @param specParam
     */
    @Override
    public void updateSpecParam(SpecParam specParam) {
        if (specParam==null){
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        时间由系统设置
        specParam.setUpdateTime(null);
        specParam.setCreateTime(null);
        int i = specParamMapper.updateByPrimaryKeySelective(specParam);
        if (i!=1){
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }

    }
}
