package com.leyou.page.service;

import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.Page.service
 * @ClassName: PageService
 * @Author: Dean
 * @Description: 商品详情实现接口
 * @Date: 2019/6/23 22:24
 * @Version: 1.0
 */
public interface PageService {

    /**
     * 加载当前页详情数据
     * @param id
     * @return
     */
    Map<String, Object> loadItemData(Long id);

    /**
     * 页面保存静态化处理
     * @param id
     */
    void createHtmlItem(Long id);
}
