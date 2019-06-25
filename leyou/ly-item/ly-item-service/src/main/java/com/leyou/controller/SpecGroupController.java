package com.leyou.controller;

import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.controller
 * @ClassName: SpecController
 * @Author: Dean
 * @Description: 商品列表
 * @Date: 2019/6/19 0:23
 * @Version: 1.0
 */

@RestController
@RequestMapping("spec")
public class SpecGroupController {

    @Autowired
    private SpecService specService;

    /**
     * 根据cid的获取组 和规格参数的集合
     * @param  id
     * @return 参数组的集合
     */
    @GetMapping("/groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupList(@RequestParam("id") Long id){
        return ResponseEntity.ok(specService.querySpecGroupById(id));
    }

    /**
     *
     * @param gid
     * @param cid
     * @param searching
     * @return 特有参数的集合
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching){
        return ResponseEntity.ok(specService.querySpecParamsById(gid,cid,searching));
    }

}
