package com.leyou.search.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.search.bean
 * @ClassName: Goods
 * @Author: Dean
 * @Description: 搜索对象 一个SPU对应一个Goods
 * @Date: 2019/6/22 2:12
 * @Version: 1.0
 */

@Data
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 1)
public class Goods {
    /**
     * 通用属性
     */
    @Id
    private Long id; // spuId
    private String subTitle;// 卖点
    private String skus;// sku信息的json结构

    /**
     * 特有属性
     */
    private String all; // 所有需要被搜索的信息，包含标题，分类，甚至品牌
    private Long brandId;// 品牌id
    private Long categoryId;// 商品3级分类id
    private Long createTime;// spu创建时间
    private Set<Long> price;// 价格
    private Map<String, Object> specs;// 可搜索的规格参数，key是参数名，值是参数值
}
