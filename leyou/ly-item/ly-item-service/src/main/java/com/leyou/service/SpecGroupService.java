package com.leyou.service;

import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service
 * @ClassName: SpecGroupService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/19 0:26
 * @Version: 1.0
 */
public interface SpecGroupService {
    List<SpecGroupDTO> querySpecGroupById(Long id);

    List<SpecParamDTO> querySpecParamsById(Long gid);
}
