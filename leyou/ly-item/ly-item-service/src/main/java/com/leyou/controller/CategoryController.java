package com.leyou.controller;

import com.leyou.dto.CategoryDTO;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.controller
 * @ClassName: CategoryController
 * @Author: Dean
 * @Description: 商品分类处理
 * @Date: 2019/6/14 21:07
 * @Version: 1.0
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    /**
     * 注入实现类
     */
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/of/parent")
    public ResponseEntity<List<CategoryDTO>> queryCategoryByParentId(
            @RequestParam(value = "pid", defaultValue = "0") Long pid) {
//        根据pid调用查询所有父节点
        List<CategoryDTO> list = categoryService.queryCategoryListByParentId(pid);

        return ResponseEntity.ok().body(list);

    }

}
