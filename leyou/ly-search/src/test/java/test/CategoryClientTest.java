package test;

import com.leyou.LySearchApplication;
import com.leyou.bean.PageBean;
import com.leyou.dto.*;
import com.leyou.item.client.ItemClient;
import com.leyou.search.bean.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
public class CategoryClientTest {

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsRepository repository;


    @Test
    public void queryByIdList() {
        List<CategoryDTO> list = itemClient.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        for (CategoryDTO category : list) {
            System.out.println("category = " + category);
        }
        Assert.assertEquals(3, list.size());
    }


    @Test
    public void test1() {
//        PageBean<SpuDTO> pageBean = itemClient.listSpuGoods(null, true, 1, 5);
//        System.out.println(pageBean);

//        BrandDTO brandDTO = itemClient.queryBrandById(2032L);
//        System.out.println("brandDTO = " + brandDTO);

//        SpuDTO spuDTO = itemClient.queryGoodsById(4L);
//        System.out.println(spuDTO);

//        BrandDTO brandDTO = itemClient.queryBrandById(1115L);
//        System.out.println("brandDTO = " + brandDTO);

        List<SpecGroupDTO> specGroupDTOS = itemClient.querySpecGroupList(76L);
        System.out.println("specGroupDTOS = " + specGroupDTOS);

    }

    @Test
    public void test() {
        List<SkuDTO> skuDTOList = itemClient.querySkuListById(3l);
//        1,准备一个map代替sku,对象就可以用map表示 接收部分数据
        List<Map<String,Object>> skuMap = new ArrayList<>();
        for (SkuDTO sku : skuDTOList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("price",sku.getPrice());
            map.put("title",sku.getTitle());
            map.put("image", StringUtils.substringBefore(sku.getImages(),"?"));
            skuMap.add(map);
        }

        System.out.println(skuMap);
    }

    /**
     * 载入数据 一次性
     */
    @Test
    public void loadData() {
        int page = 1, rows = 100, size = 0;
        do {
            try {

                // 查询spu
                PageBean<SpuDTO> result = itemClient.listSpuGoods(null, true, page, rows);
                // 取出spu
                List<SpuDTO> items = result.getItems();
                // 转换
                List<Goods> goodsList = items
                        .stream().map(searchService::buildGoods)
                        .collect(Collectors.toList());

                repository.saveAll(goodsList);
                page++;
                size = items.size();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        } while (size == 100);
    }


}