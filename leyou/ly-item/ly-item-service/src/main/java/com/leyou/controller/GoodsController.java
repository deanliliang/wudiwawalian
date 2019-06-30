package com.leyou.controller;

import com.leyou.dto.SkuDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.controller
 * @ClassName: GoodsController
 * @Author: Dean
 * @Description: 商品增删改查
 * @Date: 2019/6/20 2:09
 * @Version: 1.0
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品
     * @param spuDTO
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.saveGoods(spuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新商品
     * @param spuDTO
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.updateGoods(spuDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改商品先查商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/spu/detail")
    public ResponseEntity<SpuDetailDTO> querySpuGoodsDetail(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.querySpuDetailById(id));
    }

    /**
     * 修改商品 根据spuid查商品sku集合
     *
     * @param id
     * @return
     */
    @GetMapping("sku/of/spu")
    public ResponseEntity<List<SkuDTO>> queryGoodsSkuList(@RequestParam("id") Long id) {

        return ResponseEntity.ok(goodsService.querySkuListById(id));
    }

    /**
     * 修改商品上下架
     *
     * @param id
     * @return
     */
    @PutMapping("spu/saleable")
    public ResponseEntity<Void> changeGoodsSaleable(@RequestParam("id") Long id,
                                                    @RequestParam("saleable") Boolean saleable) {

        goodsService.changeGoodsSaleable(id, saleable);
//        int bx=1/0;
        return ResponseEntity.ok().build();
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("goods/delete/{id}")
    public ResponseEntity<Void> changeGoodsSaleable(@PathVariable("id") Long id) {

        goodsService.deleteGoodsByID(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询商品
     *
     * @param id
     * @return 商品DTO对象
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<SpuDTO> queryGoodsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(goodsService.queryGoodsById(id));
    }

}
