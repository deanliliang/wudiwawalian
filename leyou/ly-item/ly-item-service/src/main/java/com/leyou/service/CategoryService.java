package com.leyou.service;

import com.leyou.dto.CategoryDTO;
import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service
 * @ClassName: CategoryService
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/14 21:28
 * @Version: 1.0
 */
public interface CategoryService {

    /**
     *  queryCategoryListByParentId
     *  根据pid查询所有父节点
     * @param pid
     * @return List
     */
    List<CategoryDTO> queryCategoryListByParentId(Long pid);

}
