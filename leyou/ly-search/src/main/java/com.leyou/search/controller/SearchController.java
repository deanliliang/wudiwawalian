package com.leyou.search.controller;

import com.leyou.bean.PageBean;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * @author 虎哥
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索
     * @param request
     * @return
     */
    @PostMapping("/page")
    public ResponseEntity<PageBean<GoodsDTO>> search(@RequestBody SearchRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }

    /**
     * 查询过滤项
     * @param request
     * @return
     */
    @PostMapping("filter")
    public ResponseEntity<Map<String, List<?>>> queryFilters(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(searchService.queryFilters(request));
    }






}
