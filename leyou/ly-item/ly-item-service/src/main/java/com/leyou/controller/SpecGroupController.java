package com.leyou.controller;

import com.leyou.bean.SpecParam;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.service.SpecService;
import com.leyou.utils.JsonUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/spec")
public class SpecGroupController {

    @Autowired
    private SpecService specService;

    /**
     * 根据cid的获取组 和规格参数的集合
     *
     * @param id
     * @return 参数组的集合
     */
    @GetMapping("/groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupList(@RequestParam("id") Long id) {
        return ResponseEntity.ok(specService.querySpecGroupById(id));
    }

    /**搜索specGroup
     * @param gid
     * @param cid
     * @param searching
     * @return 特有参数的集合
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching) {
        return ResponseEntity.ok(specService.querySpecParamsById(gid, cid, searching));
    }

    /**新增specGroup
     * @param json
     */
    @PostMapping("/group")
    @ResponseBody
    public ResponseEntity<Void> addSpecGroup(@RequestBody JSONObject json) {

        Map<String, String> map = JsonUtils.toMap(json.toString(), String.class, String.class);
        specService.addSpec(map);
        return ResponseEntity.ok().build();
    }

    /**删除specGroup
     * @param id
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteSpecGroupByid(@PathVariable("id")Long id) {

        specService.deleteSpecGroupByid(id);
        return ResponseEntity.ok().build();
    }

    /**更新specGroup
     * @param json
     */
    @PutMapping("/group")
    @ResponseBody
    public ResponseEntity<Void> updateSpecGroup(@RequestBody JSONObject json) {
        Map<String, Object> map = JsonUtils.toMap(json.toString(), String.class, Object.class);
        specService.updateSpecGroup(map);
        return ResponseEntity.ok().build();
    }


    /**新增specparam
     * @param specParam
     */
    @PostMapping("/param")
    public ResponseEntity<Void> addSpecParam(@RequestBody SpecParam specParam) {
        specService.addSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**更新specparam
     * @param specParam
     */
    @PutMapping("/param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam) {
        specService.updateSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /** 删除specparam
     * @param id
     */
    @DeleteMapping("/param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id")Long id) {
        specService.deleteSpecParam(id);
        return ResponseEntity.ok().build();
    }


}
