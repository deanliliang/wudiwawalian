package com.leyou.upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.upload.config.OSSProperties;
import com.leyou.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.upload.service.impl
 * @ClassName: UploadServiceImpl
 * @Author: Dean
 * @Description: 图片上传实现类
 * @Date: 2019/6/21 0:24
 * @Version: 1.0
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private OSSProperties prop;

    @Autowired
    private OSS client;

    // ...

    /**
     * 支持的文件类型
     */
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg", "image/bmp", "image/jpg");

    @Override
    public String upload(MultipartFile file) {
//        图片信息的校验
//        1,图片类型校验
        String type = file.getContentType();
        if (!suffixes.contains(type)) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }

//        校验图片内容
        BufferedImage image = null;

        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        //保存图片
        // 2.1、生成保存目录,保存到nginx所在目录的html下，这样可以直接通过nginx来访问到
        File dir = new File("C:\\Program Files\\Java\\nginx-1.12.2\\html\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 2.2、保存图片
            file.transferTo(new File(dir, file.getOriginalFilename()));

            // 2.3、拼接图片地址
            return "http://image.leyou.com/" + file.getOriginalFilename();
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }

    }

    @Override
    public Map<String, Object> getSignature() {

        try {
            long expireTime = prop.getExpireTime();
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, prop.getMaxFileSize());
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, prop.getDir());

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, Object> respMap = new LinkedHashMap<>();
            respMap.put("accessId", prop.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", prop.getDir());
            respMap.put("host", prop.getHost());
            respMap.put("expire", expireEndTime);
            return respMap;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }


    }
}
