package com.leyou.controller;

import com.leyou.bean.Category;
import com.leyou.dto.CategoryDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.service.CategoryService;
import com.leyou.utils.JsonUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
     * 遍历商品分类树 修改品牌中的分类需求
     * @param id
     * @return
     */
    @GetMapping("/list/of/brand")
    public ResponseEntity<List<CategoryDTO>> queryCategoryById(
            @RequestParam("id") long id) {
//        根据品牌id调用查询所有父节点
        List<CategoryDTO> list = categoryService.queryCategoryListById(id);
        return ResponseEntity.ok().body(list);
    }

    /**
     * 添加商品分类
     * @param
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Void> addCategory(@RequestBody JSONObject json) {

        categoryService.addCategoryByJson(json);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除商品一个节点
     * @param json
     */
    @PostMapping("of/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteCategoryById(@RequestBody JSONObject json) {
        categoryService.deleteCategoryByJson(json);
        return ResponseEntity.ok().build();

    }

    /**
     * 修改商品分类节点名称
     * @param category
     */
    @PostMapping("/edit")
    public ResponseEntity<Void> updateCategoryByIdAndName(Category category) {

        categoryService.updateCategoryByIdAndName(category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改商品品牌 先查询到分类返回
     * @param id
     */
    @GetMapping("/of/brand")
    public ResponseEntity<List<CategoryDTO>> editBrandById(@RequestParam("id") String id) {

        return ResponseEntity.ok(categoryService.queryCategoryListById(Integer.parseInt(id)));
    }

}
