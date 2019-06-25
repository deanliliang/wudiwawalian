package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.upload.service
 * @ClassName: UploadService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/21 0:22
 * @Version: 1.0
 */
public interface UploadService {
    /**
     * 上传图片
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 阿里云上传OSS
     * @return
     */
    Map<String,Object> getSignature();
}
