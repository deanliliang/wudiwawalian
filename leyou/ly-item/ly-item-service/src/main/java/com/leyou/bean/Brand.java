package com.leyou.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.bean
 * @ClassName: Brand品牌实体类
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 20:07
 * @Version: 1.0
 */
@Data
@Table(name="tb_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String image;
    private Character letter;
    private Date createTime;
    private Date updateTime;

    /**
     * 修改品牌加入返回的品牌分类
     */
    private List<Category> categoryList;

}
