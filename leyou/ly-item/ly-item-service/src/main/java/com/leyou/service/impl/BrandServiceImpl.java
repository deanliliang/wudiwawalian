package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bean.Brand;
import com.leyou.bean.Brandmsg;
import com.leyou.bean.PageBean;
import com.leyou.dto.BrandDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mappers.BrandMapper;
import com.leyou.service.BrandService;
import com.leyou.utils.BeanHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service.impl
 * @ClassName: BrandServiceImpl
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 21:18
 * @Version: 1.0
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询遍历品牌
     * @param brandmsg
     * @return
     */
    @Override
    public PageBean<BrandDTO> queryBrandByBrandMsg(Brandmsg brandmsg) {
        if (null==brandmsg.getPage()||brandmsg.getPage()==0){
            brandmsg.setPage(1);
        }
        if (null==brandmsg.getRows()||brandmsg.getRows()==0){
            brandmsg.setRows(5);
        }

//        分页助手
        PageHelper.startPage(brandmsg.getPage(),brandmsg.getRows());
//        过滤条件
        Example example = new Example(Brand.class);
//        判断查询key是否为空
        if (StringUtils.isNotBlank(brandmsg.getKey())){
         //Cretiron的集合createCriteria() 后面添加条件
            example.createCriteria().orLike("name", "%" + brandmsg.getKey() + "%")
                    .orLike("id","%"+brandmsg.getKey()+"%")
                    .orEqualTo("letter", brandmsg.getKey().toUpperCase());
        }
        //Desc为true时 添加Desc排序
        if (StringUtils.isNotBlank(brandmsg.getSortBy())){
            String orderBySort = brandmsg.getSortBy()+(brandmsg.isDesc()? " DESC":" ASC");
//            按照给定的key 和是否为DESC排序
            example.setOrderByClause(orderBySort);
        }


        List<Brand> listBrands = brandMapper.selectByExample(example);
//        判断集合是否为空
        if (CollectionUtils.isEmpty(listBrands)){
            throw new  LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
//        解析分页结果
        PageInfo<Brand> page = new PageInfo<>(listBrands);
//        拷贝集合到另外一个类
        List<BrandDTO> brandDTOS = BeanHelper.copyWithCollection(listBrands, BrandDTO.class);
        return new PageBean<>(page.getTotal(),brandDTOS);
    }


    /**
     * 新增品牌和中间表 加入事务
     * @param brandDTO
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBrandByIds(BrandDTO brandDTO, List<Integer> ids) {
        Brand brand = BeanHelper.copyProperties(brandDTO, Brand.class);
        int i = brandMapper.insert(brand);
        if (i==0){
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

        int count = brandMapper.saveCategoryAndBrand(brand.getId(),ids);
        if (count!=ids.size()){
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }
}
