package com.leyou.mappers;
import com.leyou.bean.Category;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.mappers
 * @ClassName: CategoryMapper
 * @Author: Dean
 * @Description: TK 通用mapper
 * @Date: 2019/6/14 21:36
 * @Version: 1.0
 */

public interface CategoryMapper extends Mapper<Category> {

    /**
     * 根据brand id查询所有分类 连接中间表
     * @param id
     * @return
     */
    List<Category> queryCategoryListById(@Param("id") long id);

    /**
     * 删除中间表
     * @param cid
     * @return
     */
    Integer deleteByCid(@Param("cid") long cid);
}
