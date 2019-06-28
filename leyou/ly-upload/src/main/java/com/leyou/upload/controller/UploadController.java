package com.leyou.upload.controller;

import com.leyou.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.upload.controller
 * @ClassName: UploadController
 * @Author: Dean
 * @Description: 图片上传
 * @Date: 2019/6/21 0:20
 * @Version: 1.0
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片功能
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // 返回200，并且携带url路径

        return ResponseEntity.ok(this.uploadService.upload(file));
    }

    /**
     * 阿里云OSS上传
     * @return
     */
    @GetMapping("signature")
    public ResponseEntity<Map<String,Object>> getAliSignature(){
        return ResponseEntity.ok(uploadService.getSignature());
    }
}
