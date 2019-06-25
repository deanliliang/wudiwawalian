package com.leyou.service;

import com.leyou.bean.Category;
import com.leyou.dto.CategoryDTO;
import net.minidev.json.JSONObject;

import java.util.Arrays;
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

    /**
     * 查询分类
     * @param id
     * @return
     */
    List<CategoryDTO> queryCategoryListById(long id);

    /**
     * 添加分类
     * @param json
     * @return
     */
    void addCategoryByJson(JSONObject json);

    /**
     * 删除分类
     * @param json
     * @return
     */
    void deleteCategoryByJson(JSONObject json);

    /**
     * 修改分类名称
     * @param category
     */
    void updateCategoryByIdAndName(Category category);

    /**
     * 根据分类查询所有分类名
     * @param ids
     * @return
     */
    List<CategoryDTO> queryCategoryByIds(List<Long> ids);


}
