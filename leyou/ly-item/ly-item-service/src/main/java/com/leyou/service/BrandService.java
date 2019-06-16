package com.leyou.service;

import com.leyou.bean.Brandmsg;
import com.leyou.bean.PageBean;
import com.leyou.dto.BrandDTO;
import org.springframework.http.ResponseEntity;

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
    void addBrandByIds(BrandDTO brandDTO, List<Integer> ids);
}
