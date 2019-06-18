package com.leyou.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.bean
 * @ClassName: BraCatMiddle
 * @Author: Dean
 * @Description: 品牌类别中间表
 * @Date: 2019/6/18 22:55
 * @Version: 1.0
 */
@Data
@Table(name="tb_category_brand")
public class BraCatMiddle {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long cid;
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long bid;

}
