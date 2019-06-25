package com.leyou.service;

import com.leyou.bean.Brandmsg;
import com.leyou.bean.PageBean;
import com.leyou.dto.BrandDTO;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service
 * @ClassName: BrandService
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 21:12
 * @Version: 1.0
 */
public interface BrandService {

    /**
     * 查询遍历品牌
     * @param brandmsg
     * @return
     */
    PageBean<BrandDTO> queryBrandByBrandMsg(Brandmsg brandmsg);

    /**
     * 新增品牌 和中间表
     * @param brandDTO
     * @param ids
     */
    void saveBrandByIds(BrandDTO brandDTO, List<Long> ids);

    /**
     * 根据信息删除
     * @param id
     */
    void deleteBrandById(Long id);

    /**
     * 根据信息查询
     * @param id
     */
    BrandDTO queryBrandById(Long id);

    void updateBrandByBrandmsg(Brandmsg brandmsg);

    List<BrandDTO> queryBrandNameById(Long id);

    List<BrandDTO> queryByIds(List<Long> idList);
}
