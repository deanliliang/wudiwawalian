package com.leyou.controller;

import com.leyou.bean.PageBean;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;
import com.leyou.service.SpuGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.controller
 * @ClassName: SpuGoodsController
 * @Author: Dean
 * @Description: Spu商品遍历
 * @Date: 2019/6/19 19:57
 * @Version: 1.0
 */

@RestController
@RequestMapping("spu")
public class SpuGoodsController {

    @Autowired
    private SpuGoodsService spuGoodsService;

    /**
     * 获取商品列表
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageBean<SpuDTO>> listSpuGoods(
                 @RequestParam(value = "key",required = false)String key,
                 @RequestParam(value = "saleable",required = false)Boolean saleable,
                 @RequestParam(value = "page",defaultValue = "1")Integer page,
                 @RequestParam(value = "rows",defaultValue = "5")Integer rows
                 ){

        return ResponseEntity.ok(spuGoodsService.listSpuGoods(key,saleable,page,rows));
    }



}
