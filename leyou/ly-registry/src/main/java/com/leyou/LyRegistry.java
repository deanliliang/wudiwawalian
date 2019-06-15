package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ProjectName: leyou
 * @Package: com.leyou
 * @ClassName: LyRegistry
 * @Author: Dean
 * @Description: Eureka启动类
 * @Date: 2019/6/13 21:25
 * @Version: 1.0
 */

@SpringBootApplication
@EnableEurekaServer
public class LyRegistry {
    public static void main(String[] args) {
        SpringApplication.run(LyRegistry.class,args);
    }
}
