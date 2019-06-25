package com.leyou.mappers;

import com.leyou.bean.Brand;
import com.leyou.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.mappers
 * @ClassName: BrandMapper
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 22:03
 * @Version: 1.0
 */
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     *
     * @param id
     * @param ids
     * @return
     */
    int saveCategoryAndBrand(@Param("bid") Long id, @Param("ids") List<Long> ids);

    Integer deleteByBid(@Param("id") Long dtoId);

    /**
     * 根据cid查询品牌名集合
     * @param cid
     * @return
     */
    @Select("SELECT b.id, b.name, b.letter, b.image FROM tb_category_brand cb " +
            "INNER JOIN tb_brand b ON b.id = cb.brand_id " +
            "WHERE cb.category_id = #{cid}")
    List<Brand> queryBrandNameById(@Param("cid")Long cid);

}
