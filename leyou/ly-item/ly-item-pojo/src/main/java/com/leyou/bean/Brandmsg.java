package com.leyou.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.bean
 * @ClassName: Brandmsg 品牌查询接收信息封装
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 20:21
 * @Version: 1.0
 */

@Data
public class Brandmsg {
    /**
     * page：当前页，int
     * rows：每页大小，int
     * sortBy：排序字段，String
     * desc：是否为降序，boolean
     * key：搜索关键词，String
     */
    private Integer page;
    private Integer rows;
    private String sortBy;
    private boolean desc;
    private String key;

}
