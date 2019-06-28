package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bean.PageBean;
import com.leyou.bean.Spu;
import com.leyou.dto.BrandDTO;
import com.leyou.dto.CategoryDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mappers.SpuDetailMapper;
import com.leyou.mappers.SpuGoodsMapper;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import com.leyou.service.SpuGoodsService;
import com.leyou.utils.BeanHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service.impl
 * @ClassName: SpuGoodsServiceImpl
 * @Author: Dean
 * @Description: spu商品实现类
 * @Date: 2019/6/19 20:31
 * @Version: 1.0
 */

@Service
public class SpuGoodsServiceImpl implements SpuGoodsService {

    @Autowired
    private SpuGoodsMapper spuGoodsMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    /**
     * 遍历商品
     * @return pagebean类
     */
    @Override
    public PageBean<SpuDTO> listSpuGoods(String key, Boolean saleable, Integer page, Integer rows) {
//        判断是否要显示全部 rows为-1 时显示全部
        if (rows==-1){
//            查询出数据库全部条数
            int i = spuGoodsMapper.selectCounts();
            PageHelper.startPage(page, i);
        }else {
            PageHelper.startPage(page, rows);
        }
//        建立条件查询
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
//        过滤条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name","%"+key+"%");
            criteria.orLike("id","%"+key+"%");
        }

        if (saleable!=null) {
            criteria.andEqualTo("saleable",saleable);
        }
//        加入默认按照更新时间排序 降序 DESC
        example.setOrderByClause("update_time DESC");
//        查询当前页数据
        List<Spu> spusList = spuGoodsMapper.selectByExample(example);
//        判定查到数据是否为空
        if (CollectionUtils.isEmpty(spusList)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
//        分析结果
        PageInfo<Spu> spuPageInfo = PageInfo.of(spusList);
        List<SpuDTO> spuDTOS = BeanHelper.copyWithCollection(spusList, SpuDTO.class);
        //        处理品牌名和分类名


//        根据brandId获取品牌名设置到list里面
//        根据品牌分类CID获取几个分类设置到list 分类名里面
        getBrandAndCategoryName(spuDTOS);

        return new PageBean<>(spuPageInfo.getTotal(),spuDTOS);
    }


    /**
     * 添加商品分类和品牌名
     * @param spuDTOS
     */
    private void getBrandAndCategoryName(List<SpuDTO> spuDTOS){

        for (SpuDTO spuDTO : spuDTOS) {
//            根据Brand方法获取品牌name
            BrandDTO brandDTO = brandService.queryBrandById(spuDTO.getBrandId());
            spuDTO.setBrandName(brandDTO.getName());

//            //根据品牌分类CID获取几个分类设置到list 分类名里面
            // 查询分类 第一种写法
            String categoryName = categoryService.queryCategoryByIds(spuDTO.getCategoryIds())
                    .stream()
                    .map(CategoryDTO::getName).collect(Collectors.joining("/"));
            spuDTO.setCategoryName(categoryName);


            //第二种写法
//            StringBuffer sb = new StringBuffer();
//            int count = 0;
//            List<Category> categories = categoryService.queryCategoryByIds(spuDTO.getCategoryIds());
//            for (Category category : categories) {
////                拼接字符串 /
//                sb.append(category.getName());
//                if (count<(categories.size()-1)){
//                    sb.append("/");
//                    count++;
//                }
//            }
//            spuDTO.setCategoryName(sb.toString());

        }

    }


}
