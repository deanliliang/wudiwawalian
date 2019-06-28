package com.leyou.service;

import com.leyou.bean.SpecParam;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service
 * @ClassName: SpecGroupService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/19 0:26
 * @Version: 1.0
 */
public interface SpecService {

    List<SpecGroupDTO> querySpecGroupById(Long id);

    List<SpecParamDTO> querySpecParamsById(Long gid, Long cid, Boolean searching);

    void addSpec(Map<String, String> map);

    void deleteSpecGroupByid(Long id);

    void updateSpecGroup(Map<String, Object> map);

    void addSpecParam(SpecParam specParam);

    void deleteSpecParam(Long id);

    void updateSpecParam(SpecParam specParam);
}
