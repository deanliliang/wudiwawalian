package com.leyou.page.controller;

import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.Page.controller
 * @ClassName: PageController
 * @Author: Dean
 * @Description: 页面详情
 * @Date: 2019/6/23 20:47
 * @Version: 1.0
 */
@Controller
public class PageController {
    @Autowired
    private PageService pageService;

    /**
     * 跳转到商品详情页
     * @param model
     * @param id spuid
     * @return 商品详情页需要的数据
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long id){

        // 查询模型数据
        Map<String,Object> itemData = pageService.loadItemData(id);
        // 存入模型数据，因为数据较多，直接存入一个map
        model.addAllAttributes(itemData);

        return "item";
    }
}
