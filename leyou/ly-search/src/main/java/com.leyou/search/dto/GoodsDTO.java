package com.leyou.search.dto;

import lombok.Data;

/**
 * @author dean 分页结果
 */
@Data
public class GoodsDTO {
    private Long id; // spuId
    private String subTitle;// 卖点
    private String skus;// sku信息的json结构
}