package com.leyou.controller;

import com.leyou.dto.CategoryDTO;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    /**
     * 遍历商品分类树
     * @param pid
     * @return
     */
    @GetMapping("/of/parent")
    public ResponseEntity<List<CategoryDTO>> queryCategoryByParentId(
            @RequestParam(value = "pid", defaultValue = "0") Long pid) {
//        根据pid调用查询所有父节点
        List<CategoryDTO> list = categoryService.queryCategoryListByParentId(pid);

        return ResponseEntity.ok().body(list);

    }

    /**
     * 添加商品分类
     * @param isParent
//     * @param name
//     * @param parentId
//     * @param sort
     */
    @PostMapping("/add")
    public void addCategory(
           @RequestBody() CategoryDTO isParent/*,
            @RequestParam("name") String name,
            @RequestParam("parentId") Integer parentId,
            @RequestParam("sort") Integer sort*/) {

        System.out.println(isParent);
        /*System.out.println(name);
        System.out.println(parentId);
        System.out.println(sort);*/

    }

    /**
     * 删除商品一个节点
     * @param id
     */
    @RequestMapping("of/delete")
    public void deleteCategoryById(
            @RequestParam("id") String id) {

        System.out.println(id);
        System.out.println(id);
    }

    /**
     * 修改商品分类节点
     * @param id
     * @param name
     */
    @PostMapping("/edit")
    public void editCategoryByIdAndName(
            @RequestParam("id") String id,
            @RequestParam("name") String name) {

        System.out.println(id);
        System.out.println(name);

    }

}
