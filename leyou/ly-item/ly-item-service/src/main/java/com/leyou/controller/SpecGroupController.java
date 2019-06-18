package com.leyou.controller;

import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.service.SpecGroupService;
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
    private SpecGroupService specGroupService;

    @GetMapping("/groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupList(@RequestParam("id") Long id){

        return ResponseEntity.ok(specGroupService.querySpecGroupById(id));
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecList(@RequestParam("gid") Long gid){

        return ResponseEntity.ok(specGroupService.querySpecParamsById(gid));
    }
}
