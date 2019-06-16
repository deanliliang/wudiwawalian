package com.leyou.controller;

import com.leyou.bean.Brandmsg;
import com.leyou.bean.PageBean;
import com.leyou.dto.BrandDTO;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.controller
 * @ClassName: BrandController 品牌操作
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 20:24
 * @Version: 1.0
 */

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;


    /**
     * 新增品牌和中间表(品牌 分类)
     * @param brandDTO
     * @param ids
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Void> addBrandByIds(BrandDTO brandDTO,@RequestParam("ids") List<Integer> ids) {
        System.out.println(brandDTO);
        System.out.println(ids);
        brandService.addBrandByIds(brandDTO,ids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 查询遍历品牌
     * @param brandmsg
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageBean<BrandDTO>> queryBrandByBrandMsg(Brandmsg brandmsg){

        return ResponseEntity.ok(brandService.queryBrandByBrandMsg(brandmsg));
    }


}
