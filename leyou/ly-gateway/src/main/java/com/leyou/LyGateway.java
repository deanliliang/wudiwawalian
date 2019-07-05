package com.leyou.gateway;

import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.gateway
 * @ClassName: LyGateway
 * @Author: Dean
 * @Description: Zuul网关启动器
 * @Date: 2019/6/13 21:29
 * @Version: 1.0
 */

@EnableScheduling
@SpringCloudApplication
@EnableZuulProxy
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
@EnableFeignClients
public class LyGateway {
    public static void main(String[] args) {

        SpringApplication.run(LyGateway.class,args);
    }
}
