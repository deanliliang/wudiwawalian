package com.leyou.service.impl;

import com.leyou.bean.Sku;
import com.leyou.bean.Spu;
import com.leyou.bean.SpuDetail;
import com.leyou.dto.SkuDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mappers.SkuMapper;
import com.leyou.mappers.SpuDetailMapper;
import com.leyou.mappers.SpuGoodsMapper;
import com.leyou.service.GoodsService;
import com.leyou.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service.impl
 * @ClassName: GoodsServiceImpl
 * @Author: Dean
 * @Description: 商品业务实现类
 * @Date: 2019/6/20 2:20
 * @Version: 1.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuGoodsMapper spuGoodsMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;

    /**
     * 添加商品
     *
     * @param spuDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(SpuDTO spuDTO) {
//        添加Spu
//        复制属性到spu
        int count = 0;
        Spu spu = BeanHelper.copyProperties(spuDTO, Spu.class);
        spu.setId(null);
        spu.setSaleable(null);
        count = spuGoodsMapper.insertSelective(spu);
//        保存是否成功
        if (count == 0) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

//        获取传入spu你们的detail
        SpuDetailDTO spuDtoDetail = spuDTO.getSpuDetail();
//        属性拷贝到SpuDetail
        SpuDetail spuDetail = BeanHelper.copyProperties(spuDtoDetail, SpuDetail.class);
//        添加spudetail的id
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insertSelective(spuDetail);
        //        保存是否成功
        if (count == 0) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

//        添加Sku
        List<SkuDTO> skus = spuDTO.getSkus();
        for (SkuDTO skuDTO : skus) {
            Sku sku = BeanHelper.copyProperties(skuDTO, Sku.class);
//            设置sku里spuId
            sku.setSpuId(spu.getId());
            sku.setEnable(false);
            int i = skuMapper.insertSelective(sku);
            //        保存是否成功
            if (i == 0) {
                throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
            }
        }

    }


    /**
     * 商品修改先查询商品详情
     * 根据商品 spuid
     * @param id
     * @return
     */
    @Override
    public SpuDetailDTO querySpuDetailById(Long id) {
        SpuDetail spuDetail = new SpuDetail();
//        id为空不传值
        if (id != null) {
            spuDetail.setSpuId(id);
        }
        SpuDetail spuDetail1 = spuDetailMapper.selectByPrimaryKey(spuDetail);
        if (spuDetail1 == null) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        return BeanHelper.copyProperties(spuDetail1, SpuDetailDTO.class);
    }

    /**
     * 商品修改先查询商品spu
     * 根据商品 spuid
     * @param id
     * @return
     */
    @Override
    public List<SkuDTO> querySkuListById(Long id) {
//        Spu spu = new Spu();
//        Sku sku = new Sku();
//        SpuDetail spuDetail = new SpuDetail();
//        //        id为空不传值
//        if (id != null) {
//            spu.setId(id);
//            sku.setSpuId(id);
//            spuDetail.setSpuId(id);
//        }
////      1.根据id查询spu
//        Spu spu1 = spuGoodsMapper.selectByPrimaryKey(spu);
//        if (spu1 == null) {
//            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
//        }
////        转化为DTO对象
//        SpuDTO spuDTOReturn = BeanHelper.copyProperties(spu1, SpuDTO.class);
//
////      2.  通过spuid获取sku集合
//        List<Sku> skuList = skuMapper.select(sku);
//        if (CollectionUtils.isEmpty(skuList)){
//            throw  new LyException(ExceptionEnum.GOODS_NOT_FOUND);
//        }
////        转化为DTO对象
//        List<SkuDTO> skuDTOS = BeanHelper.copyWithCollection(skuList, SkuDTO.class);
////        添加到spuDTOReturn中
//        spuDTOReturn.setSkus(skuDTOS);
//
////       3. 通过spuid获取spuDetail
//        SpuDetail spuDetail1 = spuDetailMapper.selectByPrimaryKey(spuDetail);
//        if (spuDetail1==null){
//            throw  new LyException(ExceptionEnum.GOODS_NOT_FOUND);
//        }
////        转化为DTO对象
//        SpuDetailDTO spuDetailDTO = BeanHelper.copyProperties(spuDetail1, SpuDetailDTO.class);
////        添加到spuDTOReturn中
//        spuDTOReturn.setSpuDetail(spuDetailDTO);


        Sku sku = new Sku();
        if (id!=null){
            sku.setSpuId(id);
        }
//        通用mapper查询list数据
        List<Sku> skuList = skuMapper.select(sku);
//        集合为空则查询失败异常
        if (CollectionUtils.isEmpty(skuList)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(skuList, SkuDTO.class);
    }

    /**
     * 修改商品上下架
     *
     * @param id
     * @param saleable
     */
    @Override
    public void changeGoodsSaleable(Long id, Boolean saleable) {
        Spu spu = new Spu();
//        新建spu对象传入参数
        spu.setId(id);
        spu.setSaleable(saleable);
//        通用mapper进行修改
        int i = spuGoodsMapper.updateByPrimaryKeySelective(spu);
        if (i == 0) {
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }
    }

    /**
     * 删除spu tb_spu_detail sku 三张表数据
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoodsByID(Long id) {

//       1. 删除spu表数据
        Spu spu = new Spu();
        spu.setId(id);
        int i = spuGoodsMapper.deleteByPrimaryKey(spu);
//        删除条数为1时 正确
        if (i != 1) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }

//       2. 删除tb_spu_detail
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(id);
        int i1 = spuDetailMapper.deleteByPrimaryKey(spuDetail);
        //删除条数为1时 正确
        if (i1 != 1) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }

//       3. 删除sku sku表同一个id有多个数据 先查询条数再删除
        Sku sku1 = new Sku();
        sku1.setSpuId(id);
        List<Sku> skuList = skuMapper.select(sku1);
        int skuCount = skuMapper.delete(sku1);

//        查询到的skulist和删除条数不一致是报异常
        if (skuCount != skuList.size()) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
    }


    /**
     * 获取商品信息
     * @param id
     * @return
     */
    @Override
    public SpuDTO queryGoodsById(Long id) {
//        判断id的有效性
        if (id==null||id<=0){
            throw  new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        根据通用mapper主键查询
        Spu spu = spuGoodsMapper.selectByPrimaryKey(id);
        if (null==spu){
            throw  new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        SpuDTO spuDTO = BeanHelper.copyProperties(spu, SpuDTO.class);
//        加入spuDTO相关信息
        spuDTO.setSkus(querySkuListById(id));
        spuDTO.setSpuDetail(querySpuDetailById(id));

        return spuDTO;
    }

    /**
     * 更新商品
     * Todo
     * @param spuDTO
     */
    @Override
    public void updateGoods(SpuDTO spuDTO) {
        int x = 1/0;
    }

}
