package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云上传OSS
 */

//todo
//springboot 配置路径报错 ly.oss
@Data
@Component
@ConfigurationProperties("ly.oss")
public class OSSProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String host;
    private String endpoint;
    private String dir;
    private long expireTime;
    private long maxFileSize;
}