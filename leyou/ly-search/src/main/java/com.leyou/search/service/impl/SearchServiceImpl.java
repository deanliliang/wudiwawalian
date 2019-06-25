package com.leyou.search.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.bean.PageBean;
import com.leyou.dto.*;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.client.ItemClient;
import com.leyou.search.bean.Goods;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;
import com.leyou.search.service.SearchService;
import com.leyou.utils.BeanHelper;
import com.leyou.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.search.service.impl
 * @ClassName: SearchService
 * @Author: Dean
 * @Description: 实现类
 * @Date: 2019/6/22 2:20
 * @Version: 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 搜索查询
     * @param request
     * @return GoodsDTO显示数据
     */
    @Override
    public PageBean<GoodsDTO> search(SearchRequest request) {
        String key = request.getKey();
//        key不能为空
        if (StringUtils.isBlank(key)){
            throw  new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        创建搜索原生构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        组织条件
//        source过滤 控制字段数量
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
//        搜索条件 match匹配查询
        QueryBuilder buildBasicQuery = buildBasicQuery(request);
        queryBuilder.withQuery(buildBasicQuery);
//        分页条件
        int page = request.getPage()-1;
        int size = request.getSize();
        queryBuilder.withPageable(PageRequest.of(page,size));

//        搜索结果
        AggregatedPage<Goods> pageResult = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

//        解析结果
//        解析分页数据
        long total = pageResult.getTotalElements();
        int totalPages = pageResult.getTotalPages();
        List<Goods> list = pageResult.getContent();
//        转发DTO对象
        List<GoodsDTO> goodsDTOS = BeanHelper.copyWithCollection(list, GoodsDTO.class);
        return new PageBean<>(total,totalPages,goodsDTOS);
    }

    /**
     * 根据搜索过滤条件查询
     * @param request
     * @return
     */
    @Override
    public Map<String, List<?>> queryFilters(SearchRequest request) {
        // 0.组织过滤项
        Map<String, List<?>> filterList = new LinkedHashMap<>();

        // 1.原生查询构建器，准备查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 1.0.source过滤，控制返回字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[0], new String[0]));
        // 1.1.搜索条件
        QueryBuilder basicQuery = buildBasicQuery(request);
        queryBuilder.withQuery(basicQuery);
        // 1.2.分页条件
        queryBuilder.withPageable(PageRequest.of(0, 1));
        // 1.3.添加聚合条件
        // 1.3.1.分类的聚合
        String categoryAggName = "categoryAgg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("categoryId"));
        // 1.3.2.品牌的聚合
        String brandAggName = "brandAgg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 2.发起请求，获取结果
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

        // 3.解析数据，获取聚合结果
        Aggregations aggs = result.getAggregations();
        // 3.1.取出分类的聚合
        List<Long> idList = handleCategoryAggs(aggs.get(categoryAggName), filterList);
        // 3.2.取出品牌聚合
        handleBrandAggs(aggs.get(brandAggName), filterList);

        // 4.判断分类是否只剩下一个
        if (idList != null && idList.size() == 1) {
            // 处理规格的聚合, 三个参数：分类的id、查询的条件、过滤项的map集合
            handleSpecsAgg(idList.get(0), basicQuery, filterList);
        }
        return filterList;
    }

    private void handleSpecsAgg(Long cid, QueryBuilder basicQuery, Map<String, List<?>> filterList) {
        // 1.查询分类下的searching为true的规格的名称
        List<SpecParamDTO> params = itemClient.querySpecParams(null, cid, true);

        // 2.原生查询构建器，准备查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 2.0.source过滤，控制返回字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[0], new String[0]));
        // 2.1.搜索条件
        queryBuilder.withQuery(basicQuery);
        // 2.2.分页条件
        queryBuilder.withPageable(PageRequest.of(0, 1));
        // 2.3.添加聚合条件
        for (SpecParamDTO param : params) {
            // 取出参数的名称作为聚合名称
            String aggName = param.getName();
            queryBuilder.addAggregation(AggregationBuilders.terms(aggName).field("specs." + aggName));
        }

        // 3.发起请求，获取结果
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

        // 4.解析数据，获取聚合结果
        Aggregations aggs = result.getAggregations();
        for (SpecParamDTO param : params) {
            // 取出参数的名称作为聚合名称
            String aggName = param.getName();
            Terms terms = aggs.get(aggName);
            // 解析聚合结果
            List<String> list = terms.getBuckets().stream()
                    .map(Terms.Bucket::getKeyAsString)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
            // 把规格集合放入map
            filterList.put(aggName, list);
        }
    }

    private void handleBrandAggs(Terms terms, Map<String, List<?>> filterList) {
        // 获取所有品牌的id
        List<Long> idList = terms.getBuckets().stream()
                .map(Terms.Bucket::getKeyAsNumber).map(Number::longValue)
                .collect(Collectors.toList());
        // 根据品牌id的集合，查询品牌对象的集合
        List<BrandDTO> list = itemClient.queryBrandByIds(idList);
        // 把品牌集合放入map
        filterList.put("品牌", list);
    }

    private List<Long> handleCategoryAggs(Terms terms, Map<String, List<?>> filterList) {
        // 获取所有分类的id
        List<Long> idList = terms.getBuckets().stream()
                .map(Terms.Bucket::getKeyAsNumber).map(Number::longValue)
                .collect(Collectors.toList());
        // 根据分类id的集合，查询分类对象的集合
        List<CategoryDTO> list = itemClient.queryCategoryByIds(idList);
        // 把分类集合放入map
        filterList.put("分类", list);
        return idList;
    }

    private QueryBuilder buildBasicQuery(SearchRequest request) {
//        // 构建基本的match查询
//        return QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND);

        // 1.构建布尔查询
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 2.封装关键字查询
        queryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));
        // 3.封装过滤条件

        Map<String, String> map = request.getFilter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            // 处理特殊的key
            if("品牌".equals(key)){
                key = "brandId";
            }else if("分类".equals(key)){
                key = "categoryId";
            }else{
                key = "specs." + key;
            }
            // 添加过滤条件
            queryBuilder.filter(QueryBuilders.termQuery(key, entry.getValue()));
        }
        return queryBuilder;
    }

    /**
     * 此方法在测试类中运行一次
     * @param spu
     * @return
     */
    @Override
    public Goods buildGoods(SpuDTO spu) {
// 1 商品相关搜索信息的拼接：名称、分类、品牌、规格信息等
        // 1.1 分类
        String categoryNames = itemClient.queryCategoryByIds(spu.getCategoryIds())
                .stream().map(CategoryDTO::getName).collect(Collectors.joining(","));
        // 1.2 品牌
        BrandDTO brand = itemClient.queryBrandById(spu.getBrandId());
        // 1.3 名称等，完成拼接
        String all = spu.getName() + categoryNames + brand.getName();

        // 2 spu下的所有sku的JSON数组
        List<SkuDTO> skuList = itemClient.querySkuListById(spu.getId());
        // 准备一个集合，用map来代替sku，只需要sku中的部分数据
        List<Map<String, Object>> skuMap = new ArrayList<>();
        for (SkuDTO sku : skuList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("price", sku.getPrice());
            map.put("title", sku.getTitle());
            map.put("image", StringUtils.substringBefore(sku.getImages(), ","));
            skuMap.add(map);
        }

        // 3 当前spu下所有sku的价格的集合
        Set<Long> price = skuList.stream().map(SkuDTO::getPrice).collect(Collectors.toSet());

        // 4 当前spu的规格参数
        Map<String, Object> specs = new HashMap<>();

        // 4.1 获取规格参数key，来自于SpecParam中当前分类下的需要搜索的规格
        List<SpecParamDTO> specParams = itemClient.querySpecParams(null, spu.getCid3(), true);
        // 4.2 获取规格参数的值，来自于spuDetail
        SpuDetailDTO spuDetail = itemClient.querySpuDetailById(spu.getId());
        // 4.2.1 通用规格参数值
        Map<Long, Object> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(), Long.class, Object.class);
        // 4.2.2 特有规格参数值
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });

        for (SpecParamDTO specParam : specParams) {
            // 获取规格参数的名称
            String key = specParam.getName();
            // 获取规格参数值
            Object value = null;
            // 判断是否是通用规格
            if (specParam.getGeneric()) {
                // 通用规格
                value = genericSpec.get(specParam.getId());
            } else {
                // 特有规格
                value = specialSpec.get(specParam.getId());
            }
            // 判断是否是数字类型
            if(specParam.getNumeric()){
                // 是数字类型，分段
                value = chooseSegment(value, specParam);
            }
            // 添加到specs
            specs.put(key, value);
        }

        Goods goods = new Goods();
        // 从spu对象中拷贝与goods对象中属性名一致的属性
        goods.setBrandId(spu.getBrandId());
        goods.setCategoryId(spu.getCid3());
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());
        goods.setCreateTime(spu.getCreateTime().getTime());
        goods.setSkus(JsonUtils.toString(skuMap));// spu下的所有sku的JSON数组
        goods.setSpecs(specs); // 当前spu的规格参数
        goods.setPrice(price); // 当前spu下所有sku的价格的集合
        goods.setAll(all);// 商品相关搜索信息的拼接：标题、分类、品牌、规格信息等
        return goods;
    }


    private String chooseSegment(Object value, SpecParamDTO p) {
        if (value == null || StringUtils.isBlank(value.toString())) {
            return "其它";
        }
        double val = parseDouble(value.toString());
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = parseDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = parseDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    private double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
