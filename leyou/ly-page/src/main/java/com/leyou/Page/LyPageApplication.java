package com.leyou.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.Page
 * @ClassName: LyPageApplication
 * @Author: Dean
 * @Description: thymeleaf 启动类
 * @Date: 2019/6/23 20:35
 * @Version: 1.0
 */
@EnableDiscoveryClient
@EnableFeignClients("com.leyou.item.client")
@SpringBootApplication
public class LyPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyPageApplication.class,args);
    }
}
