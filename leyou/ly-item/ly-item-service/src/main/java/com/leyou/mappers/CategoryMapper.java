package com.leyou.mappers;
import com.leyou.bean.Category;
import com.leyou.dto.CategoryDTO;
import com.leyou.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 根据brand id查询所有分类 连接中间表
     * @param id
     * @return
     */
//    List<Category> queryCategoryListById(@Param("id") long id);

    /**
     * 删除中间表
     * @param cid
     * @return
     */
    Integer deleteByCid(@Param("cid") long cid);

    /**
     * 通过brandid查询所有分类名集合
     * @param id
     * @return
     */
    @Select("SELECT c.* FROM tb_category c INNER JOIN tb_category_brand tb " +
            "INNER JOIN tb_brand b on c.id=tb.category_id and b.id=tb.brand_id " +
            "WHERE tb.brand_id = #{id}")
    List<Category> queryCategoryListByBrandId(@Param("id") long id);

}
