package com.leyou.item.client;

import com.leyou.bean.PageBean;
import com.leyou.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author HuYi
 */
@FeignClient("item-service")
public interface ItemClient {
    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("brand/of/brand/")
    BrandDTO queryBrandById(@RequestParam("id") Long id);

    /**
     * 根据id的集合查询商品分类
     * @param idList 商品分类的id集合
     * @return 分类集合
     */
    @GetMapping("/category/list")
    List<CategoryDTO> queryCategoryByIds(@RequestParam("ids") List<Long> idList);

    /**
     * 分页查询spu
     * @param page 当前页
     * @param rows 每页大小
     * @param saleable 上架商品或下降商品
     * @param key 关键字
     * @return 当前页商品数据
     */
    @GetMapping("/spu/page")
    PageBean<SpuDTO> listSpuGoods(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows);

    /**
     * 根据spuID查询spuDetail
     * @param id spuID
     * @return SpuDetail
     */
    @GetMapping("/spu/detail")
    SpuDetailDTO querySpuDetailById(@RequestParam("id") Long id);



    /**
     * 根据spuID查询sku
     * @param id spuID
     * @return sku的集合
     */
    @GetMapping("sku/of/spu")
    List<SkuDTO> querySkuListById(@RequestParam("id") Long id);

    /**
     * 查询规格参数
     * @param gid 组id
     * @param cid 分类id
     * @param searching 是否用于搜索
     * @return 规格组集合
     */
    @GetMapping("/spec/params")
    List<SpecParamDTO> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * 根据品牌id批量查询品牌
     * @param idList 品牌id的集合
     * @return 品牌的集合
     */
    @GetMapping("/brand/list")
    List<BrandDTO> queryBrandByIds(@RequestParam("ids") List<Long> idList);

    /**
     * 查询商品
     *
     * @param id
     * @return 商品DTO对象
     */
    @GetMapping("/spu/{id}")
    SpuDTO queryGoodsById(@PathVariable("id") Long id);

    /**
     * 根据bid的获取品牌 分类表的集合
     * @param bid
     * @return 分类集合
     */
    @GetMapping("/category/of/bid")
    List<CategoryDTO> queryCategoryListByBid(@RequestParam("bid") Long bid);

    /**
     * 根据cid3的获取组 和规格参数的集合
     * @param id
     * @return 组 规格集合
     */
    @GetMapping("/spec/groups/of/category")
    List<SpecGroupDTO> querySpecGroupList(@RequestParam("id") Long id);
}