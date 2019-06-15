package com.leyou.service.impl;

import com.leyou.bean.Category;
import com.leyou.dto.CategoryDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mapper.CategoryMapper;
import com.leyou.service.CategoryService;
import com.leyou.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

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
    @Override
    public List<CategoryDTO> queryCategoryListByParentId(Long pid) {

//        新建分类对象传入pid值
        Category category = new Category();
        category.setParentId(pid);
//        mapper会根据已有值查询数据
        List<Category> categoryList = categoryMapper.select(category);
        List<CategoryDTO> list = BeanHelper.copyWithCollection(categoryList, CategoryDTO.class);
        //list判空
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
}
