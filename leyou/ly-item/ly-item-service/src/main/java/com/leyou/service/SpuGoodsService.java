package com.leyou.service;

import com.leyou.bean.PageBean;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.service
 * @ClassName: SpuService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/19 20:28
 * @Version: 1.0
 */
public interface SpuGoodsService {

    PageBean<SpuDTO> listSpuGoods(String key, Boolean saleable, Integer page, Integer rows);


}
