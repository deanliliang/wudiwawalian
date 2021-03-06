package com.leyou.mappers;

import com.leyou.bean.Spu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.mappers
 * @ClassName: SpuGoodsMapper
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/19 20:36
 * @Version: 1.0
 */
public interface SpuGoodsMapper extends Mapper<Spu> {
    @Select("SELECT count(*) FROM tb_spu")
    int selectCounts();
}
