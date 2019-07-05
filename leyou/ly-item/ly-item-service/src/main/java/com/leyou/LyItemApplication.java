package com.leyou;

import com.leyou.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ProjectName: leyou
 * @Package: com.leyou
 * @ClassName: LyItemApplication
 * @Author: Dean
 * @Description: ly-item-service启动类
 * @Date: 2019/6/13 21:50
 * @Version: 1.0
 */

@SpringBootApplication //spring启动项
@EnableDiscoveryClient //让注册中心扫描发现 提供服务
@MapperScan("com.leyou.mappers") //扫描Mapper类
@EnableFeignClients
@EnableConfigurationProperties(JwtProperties.class)
@EnableScheduling
public class LyItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class,args);
    }
}
