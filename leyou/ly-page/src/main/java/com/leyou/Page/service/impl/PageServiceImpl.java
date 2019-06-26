package com.leyou.page.service.impl;

import com.leyou.dto.*;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.client.ItemClient;
import com.leyou.page.service.PageService;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.Page.service.impl
 * @ClassName: PageServiceImpl
 * @Author: Dean
 * @Description: 商品详情实现类
 * @Date: 2019/6/23 22:25
 * @Version: 1.0
 */
@Slf4j
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${ly.static.itemDir}")
    private String itemDir;

    @Value("${ly.static.itemTemplate}")
    private String itemTemplate;



    @Override
    public void createHtmlItem(Long id){
        // 上下文，准备模型数据
        Context context = new Context();
        // 调用之前写好的加载数据方法
        context.setVariables(loadItemData(id));
        // 准备文件路径
        File dir = new File(itemDir);
        // 创建失败，抛出异常
        if (!dir.exists()){
            if (!dir.mkdirs()){
                log.error("【静态页服务】创建静态页目录失败，目录地址：{}",dir.getAbsolutePath());
                throw new LyException(ExceptionEnum.DIRECTORY_WRITER_ERROR);
            }
        }
        File filePath = new File(dir,id+".html");
        // 准备输出流
        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")){
            templateEngine.process(itemTemplate,context,printWriter);

        }catch (Exception e){
            log.error("页面静态文件写入失败",id,e);
            throw new LyException(ExceptionEnum.FILE_WRITER_ERROR);
        }

    }

    /**
     * 商品详情页数据
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> loadItemData(Long id) {
//        设置一个map接收数据最终返回
        Map<String, Object> map = new HashMap<>();
//        根据spuid查询商品和品牌信息
        SpuDTO spuDTO = itemClient.queryGoodsById(id);
        BrandDTO brandDTO = itemClient.queryBrandById(spuDTO.getBrandId());
//        - categories：商品分类对象集合
        List<CategoryDTO> categories = itemClient.queryCategoryListByBid(brandDTO.getId());
        map.put("categories",categories);

//        - brand：品牌对象
        map.put("brand",brandDTO);

//        - spuName：应该 是spu表中的name属性
        map.put("spuName",spuDTO.getName());

//        - subTitle：spu中 的副标题
        map.put("subTitle",spuDTO.getSubTitle());

//        - detail：商品详情SpuDetail
        map.put("detail",spuDTO.getSpuDetail());

//        - skus：商品spu下的sku集合
        map.put("skus",spuDTO.getSkus());
//        - specs：规格参数这个比较 特殊：
        List<SpecGroupDTO> SpecGroupList = itemClient.querySpecGroupList(spuDTO.getCid3());
        map.put("specs",SpecGroupList);
        return map;
    }

    /**
     * 删除本地静态html
     * @param id
     */
    @Override
    public void deleteHtmlItem(Long id) {
        File file = new File(itemDir, id + ".html");
        if (file.exists()){
               if (!file.delete()){
                   log.error("【静态页服务】静态页删除失败，商品id：{}", id);
                   throw new LyException(ExceptionEnum.FILE_WRITER_ERROR);
               }
        }
    }
}
