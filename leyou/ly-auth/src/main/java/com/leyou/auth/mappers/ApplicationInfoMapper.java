package com.leyou.auth.mappers;


import com.leyou.auth.bean.ApplicationInfo;
import com.leyou.mapper.BaseMapper;

import java.util.List;

/**
 * @author
 */
public interface ApplicationInfoMapper extends BaseMapper<ApplicationInfo> {

    List<Long> queryTargetIdListByAppId(Long id);
}