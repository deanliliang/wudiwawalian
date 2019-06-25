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
     *
     * @param brandmsg
     * @return
     */
    @Override
    public PageBean<BrandDTO> queryBrandByBrandMsg(Brandmsg brandmsg) {
        if (null == brandmsg.getPage() || brandmsg.getPage() == 0) {
            brandmsg.setPage(1);
        }
        if (null == brandmsg.getRows() || brandmsg.getRows() == 0) {
            brandmsg.setRows(5);
        }

//        分页助手
        PageHelper.startPage(brandmsg.getPage(), brandmsg.getRows());
//        过滤条件
        Example example = new Example(Brand.class);
//        判断查询key是否为空
        if (StringUtils.isNotBlank(brandmsg.getKey())) {
            //Cretiron的集合createCriteria() 后面添加条件
            example.createCriteria().orLike("name", "%" + brandmsg.getKey() + "%")
                    .orLike("id", "%" + brandmsg.getKey() + "%")
                    .orEqualTo("letter", brandmsg.getKey().toUpperCase());
        }
        //Desc为true时 添加Desc排序
        if (StringUtils.isNotBlank(brandmsg.getSortBy())) {
            String orderBySort = brandmsg.getSortBy() + (brandmsg.isDesc() ? " DESC" : " ASC");
//            按照给定的key 和是否为DESC排序
            example.setOrderByClause(orderBySort);
        }



        List<Brand> listBrands = brandMapper.selectByExample(example);

//        判断集合是否为空
        if (CollectionUtils.isEmpty(listBrands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
//        解析分页结果
        PageInfo<Brand> page = new PageInfo<>(listBrands);
//        拷贝集合到另外一个类
        List<BrandDTO> brandDTOS = BeanHelper.copyWithCollection(listBrands, BrandDTO.class);
        return new PageBean<>(page.getTotal(), brandDTOS);
    }


    /**
     * 新增修改 品牌和中间表 加入事务
     *
     * @param brandDTO
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBrandByIds(BrandDTO brandDTO, List<Long> ids) {

//        判断当前是更新还是新增 不为空则是更新
        Long dtoId = brandDTO.getId();
        if (dtoId != null) {
//            转化为brand
            Brand brand = BeanHelper.copyProperties(brandDTO, Brand.class);
            int i = brandMapper.updateByPrimaryKeySelective(brand);

//            根据bid删除原中间表
            Integer deleteCount = brandMapper.deleteByBid(dtoId);
            if (deleteCount == 0) {
                throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
            }

//            根据新的ids 在中间表加入新的表数据
            i = brandMapper.saveCategoryAndBrand(brand.getId(), ids);
            if (i != ids.size()) {
                throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
            }
        } else {

            //        转化类
            Brand brand = BeanHelper.copyProperties(brandDTO, Brand.class);
//        新增品牌
            Integer i = brandMapper.insertSelective(brand);
            if (i == 0) {
                throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
            }
//        新增中间表
            i = brandMapper.saveCategoryAndBrand(brand.getId(), ids);
            if (i != ids.size()) {
                throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
            }
        }
    }

    /**
     * 删除品牌 删除中间表
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrandById(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
//        删除品牌
        int i = brandMapper.delete(brand);
        if (i == 0) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
        //删除分类和品牌中间表
        Integer bid1 = brandMapper.deleteByBid(id);
        if (bid1 == 0) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public BrandDTO queryBrandById(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
//        根据brand查询
        List<Brand> brands = brandMapper.select(brand);
        if (brands.size() != 1) {
            throw new LyException(ExceptionEnum.INVALID_NOTIFY_PARAM);
        }
//        取出集合第一个也是唯一一个值
        Brand brand1 = brands.get(0);
//        属性复制
        BrandDTO brandDTO = BeanHelper.copyProperties(brand1, BrandDTO.class);
        if (brandDTO==null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brandDTO;
    }

    /**
     * @param brandmsg
     */
    @Override
    public void updateBrandByBrandmsg(Brandmsg brandmsg) {
//        转换成brand
        Brand brand = BeanHelper.copyProperties(brandmsg, Brand.class);
        int insert = brandMapper.insert(brand);
        if (insert != 1) {
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }
    }

    /**
     * 根据分类对象里面的id查询品牌名集合
     * @param id
     * @return BrandDTO集合
     */
    @Override
    public List<BrandDTO> queryBrandNameById(Long id) {
        List<Brand> brands = brandMapper.queryBrandNameById(id);
//        判断集合是否为空

        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        return BeanHelper.copyWithCollection(brands,BrandDTO.class);
    }

    /**
     * 根据ids查询返回品牌集合
     * @param idList
     * @return
     */
    @Override
    public List<BrandDTO> queryByIds(List<Long> idList) {
        List<Brand> brandList = brandMapper.selectByIdList(idList);
//        集合为空或者添加数量和ids集合数量不等则有异常
        if (CollectionUtils.isEmpty(brandList)||idList.size()!=brandList.size()){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(brandList, BrandDTO.class);
    }
}
