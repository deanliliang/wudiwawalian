package com.leyou.search.repository;
import com.leyou.search.bean.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.search.repository
 * @ClassName: GoodsRepository
 * @Author: Dean
 * @Description: 构建goods对象
 * @Date: 2019/6/22 2:10
 * @Version: 1.0
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
