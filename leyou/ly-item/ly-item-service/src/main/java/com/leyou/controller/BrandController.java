package com.leyou.controller;

import com.leyou.bean.Brandmsg;
import com.leyou.bean.PageBean;
import com.leyou.dto.BrandDTO;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.controller
 * @ClassName: BrandController 品牌操作
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 20:24
 * @Version: 1.0
 */

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 新增品牌和中间表(品牌 分类)
     *
     * @param brandDTO
     * @param ids
     * @return
     */
    @PutMapping("save")
    public ResponseEntity<Void> saveBrandByIds(BrandDTO brandDTO, @RequestParam("cids") List<Long> ids) {

        brandService.saveBrandByIds(brandDTO, ids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新品牌和中间表(品牌 分类)
     *
     * @param brandDTO
     * @param ids
     * @return
     */
    @PostMapping("save")
    public ResponseEntity<Void> updateBrandByIds(BrandDTO brandDTO, @RequestParam("cids") List<Long> ids) {

        brandService.saveBrandByIds(brandDTO, ids);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询遍历品牌
     *
     * @param brandmsg
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageBean<BrandDTO>> queryBrandByBrandMsg(Brandmsg brandmsg) {
        return ResponseEntity.ok(brandService.queryBrandByBrandMsg(brandmsg));
    }

    /**
     * 修改品牌

     * @param brandmsg
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Void> updateBrandByBrandMsg(Brandmsg brandmsg) {
//        服务器成功处理了请求，但不需要返回任何实体内容 RESET_CONTENT
        brandService.updateBrandByBrandmsg(brandmsg);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }

    /**
     * 删除品牌 和中间表数据
     *
     * @param
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBrandAndMidById(@PathVariable("id") Long id) {

//        删除中间表
        brandService.deleteBrandById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 查询品牌
     *
     * @param id
     * @return BrandDTO
     */
    @GetMapping("/of/brand")
    public ResponseEntity<BrandDTO> queryBrandById(Long id) {
        BrandDTO b = brandService.queryBrandById(id);
//        返回查询到的BrandDTO
        return ResponseEntity.status(HttpStatus.OK).body(b);
    }

    /**
     * 查询品牌
     *
     * @param id
     * @return BrandDTO
     */
    @GetMapping("/of/category")
    public ResponseEntity<List<BrandDTO>> queryCategoryById(@RequestParam("id") Long id) {


//        返回查询到的BrandDTO集合
        return ResponseEntity.ok(brandService.queryBrandNameById(id));
    }

    /**
     * 根据id的集合查询品牌
     * @param idList 品牌id的集合
     * @return 品牌的集合
     */
    @GetMapping("/list")
    public ResponseEntity<List<BrandDTO>> queryBrandByIds(@RequestParam("ids") List<Long> idList){
        return ResponseEntity.ok(brandService.queryByIds(idList));
    }


}
