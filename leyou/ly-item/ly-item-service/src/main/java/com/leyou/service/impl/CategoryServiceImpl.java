package com.leyou.service.impl;

import com.leyou.bean.BraCatMiddle;
import com.leyou.bean.Category;
import com.leyou.dto.CategoryDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mappers.BraCatMapper;
import com.leyou.mappers.CategoryMapper;
import com.leyou.service.CategoryService;
import com.leyou.utils.BeanHelper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service.impl
 * @ClassName: CategoryServiceImpl
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/14 21:30
 * @Version: 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BraCatMapper braCatMapper;

    @Override
    public List<CategoryDTO> queryCategoryListByParentId(Long pid) {

//        新建分类对象传入pid值
        Category category = new Category();
        category.setParentId(pid);
//        mapper会根据已有值查询数据
        List<Category> categoryList = categoryMapper.select(category);
        List<CategoryDTO> categoryDTOS = getCategoryDTOS(categoryList);
        return categoryDTOS;
    }

    /**
     * 根据品牌id查询
     *
     * @param id
     * @return 分类集合
     */
    @Override
    public List<CategoryDTO> queryCategoryListById(long id) {
////        根据brand id找中间表 在查找分类
//        List<Category> categorylist = categoryMapper.queryCategoryListById(id);
//
//        //拷贝属性
//        List<CategoryDTO> categoryDTOS = getCategoryDTOS(categorylist);
//        return categoryDTOS;

        // 根据父类目id查询子类目
        List<Category> categoryList = categoryMapper.queryCategoryListByBrandId(id);

        // 健壮性判断
        if (CollectionUtils.isEmpty(categoryList)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        // 转换DTO并返回
        return BeanHelper.copyWithCollection(categoryList, CategoryDTO.class);
    }


    /**
     * 类型转换到DTO
     *
     * @param categorylist
     * @return
     */
    private List<CategoryDTO> getCategoryDTOS(List<Category> categorylist) {
        List<CategoryDTO> categoryDTOS = BeanHelper.copyWithCollection(categorylist, CategoryDTO.class);
        if (CollectionUtils.isEmpty(categoryDTOS)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categoryDTOS;
    }

    /**
     * 添加分类
     *
     * @param json
     * @return
     */
    @Override
    public void addCategoryByJson(JSONObject json) {
//        json转化为hashmap
        HashMap data = (HashMap) json.get("data");
        Category category = new Category();
//        属性加入到新建分类中
        Integer parentId = (Integer) data.get("parentId");

        String id = parentId + "";
        category.setParentId(Long.parseLong(id));
        category.setName((String) data.get("name"));
        category.setIsParent((Boolean) data.get("isParent"));
        category.setSort((Integer) data.get("sort"));

//        使用通用mapper添加
        int i = categoryMapper.insertSelective(category);
//        判断保存是否成功
        if (i != 1) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 删除分类 和中间表数据
     *
     * @param json
     */
    @Override
    public void deleteCategoryByJson(JSONObject json) {

        //json转化为hashmap
        HashMap data = (HashMap) json.get("params");
        Category category = new Category();
//        属性加入到新建分类中
        Integer id = (Integer) data.get("id");
        String id1 = id + "";
//        字符串转换成Long
        long cid = Long.parseLong(id1);
        category.setId(cid);
//        使用通用mapper删除
        int delete = categoryMapper.delete(category);
        //        判断删除是否成功
        if (delete != 1) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }

//        删除中间表数据
//        先判断此cid时候有中间表数据
        BraCatMiddle braCatMiddle = new BraCatMiddle();
        braCatMiddle.setCid(cid);
//        查询中间表是否存在数据
        List<BraCatMiddle> braCatList = braCatMapper.select(braCatMiddle);
//        存在数据则操作删除
        if (braCatList.size() > 0) {
            Integer count = categoryMapper.deleteByCid(cid);
            if (count == 0 || count == null) {
                throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
            }
        }

    }

    /**
     * 修改分类名称
     *
     * @param category
     */
    @Override
    public void updateCategoryByIdAndName(Category category) {
        //加入更新时间
        category.setUpdateTime(new Date());
        int i = categoryMapper.updateByPrimaryKeySelective(category);
        if (i != 1) {
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }
    }

    /**
     * @param ids
     * @return 根据ids获取所有分类名
     */
    @Override
    public List<CategoryDTO> queryCategoryByIds(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);
        // 判断是否为空
        if (CollectionUtils.isEmpty(categories)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(categories, CategoryDTO.class);
    }


}
