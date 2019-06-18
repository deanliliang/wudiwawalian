package com.leyou.mappers;

import com.leyou.bean.Brand;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

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
public interface BrandMapper extends Mapper<Brand> {
    /**
     *
     * @param id
     * @param ids
     * @return
     */
    int saveCategoryAndBrand(@Param("bid") Long id, @Param("ids") List<Long> ids);

    Integer deleteByBid(@Param("id") Long dtoId);

}
