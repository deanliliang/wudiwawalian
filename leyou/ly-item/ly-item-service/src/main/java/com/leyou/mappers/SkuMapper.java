package com.leyou.mappers;

import com.leyou.bean.Sku;
import com.leyou.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.mappers
 * @ClassName: SkuMapper
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/20 9:33
 * @Version: 1.0
 */
public interface SkuMapper extends BaseMapper<Sku> {


    Integer insertSkuList(@Param("skuList") List<Sku> skuList);
}
