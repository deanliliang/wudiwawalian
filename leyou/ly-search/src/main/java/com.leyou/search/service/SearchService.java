package com.leyou.search.service;

import com.leyou.bean.PageBean;
import com.leyou.dto.SpuDTO;
import com.leyou.search.bean.Goods;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.search.service
 * @ClassName: SearchService
 * @Author: Dean
 * @Description: Spu转变为Goods进行保存
 * @Date: 2019/6/22 2:18
 * @Version: 1.0
 */
public interface SearchService {

    Goods buildGoods(SpuDTO spu);

    PageBean<GoodsDTO> search(SearchRequest request);



    Map<String, List<?>> queryFilters(SearchRequest request);

    void createIndex(Long id);

    void deleteIndex(Long id);
}
