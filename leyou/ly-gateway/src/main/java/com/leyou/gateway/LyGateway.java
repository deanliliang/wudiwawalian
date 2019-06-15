package com.leyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.gateway
 * @ClassName: LyGateway
 * @Author: Dean
 * @Description: Zuul网关启动器
 * @Date: 2019/6/13 21:29
 * @Version: 1.0
 */

@SpringCloudApplication
@EnableZuulProxy
public class LyGateway {
    public static void main(String[] args) {

        SpringApplication.run(LyGateway.class,args);
    }
}
