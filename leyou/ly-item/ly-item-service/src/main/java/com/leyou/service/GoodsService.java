package com.leyou.service;

import com.leyou.dto.SkuDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service
 * @ClassName: GoodsService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/20 2:19
 * @Version: 1.0
 */
public interface GoodsService {
    /**
     * 保存商品业务
     * @param spuDTO
     */
    void saveGoods(SpuDTO spuDTO);

    /**
     * 查询商品详情业务
     * @param id
     */
    SpuDetailDTO querySpuDetailById(Long id);

    /**
     * 查询商品业务
     * @param id
     */
    List<SkuDTO> querySkuListById(Long id);

    /**
     * 修改商品信息
     * @param id
     * @param saleable
     */
    void changeGoodsSaleable(Long id, Boolean saleable);

    /**
     * 删除商品
     * @param id
     */
    void deleteGoodsByID(Long id);

    /**
     * 更新商品信息
     * @param spuDTO
     */
    void updateGoods(SpuDTO spuDTO);

    /**
     * 获取商品信息
     * @param id
     * @return
     */
    SpuDTO queryGoodsById(Long id);

    List<SkuDTO> querySkuByIds(List<Long> ids);
}
