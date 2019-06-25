package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.upload
 * @ClassName: LyUpLoadApplication
 * @Author: Dean
 * @Description: 上传图片启动类
 * @Date: 2019/6/21 0:17
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyUploadApplication.class, args);
    }
}
