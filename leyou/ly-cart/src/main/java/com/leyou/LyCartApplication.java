package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ProjectName: leyou
 * @Package: com.leyou
 * @ClassName: LyCartApplication
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 17:05
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyCartApplication.class, args);
    }
}
