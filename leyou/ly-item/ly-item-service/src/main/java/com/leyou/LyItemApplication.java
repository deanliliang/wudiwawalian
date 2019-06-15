package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@MapperScan("com.leyou.mapper") //扫描Mapper类
public class LyItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class,args);
    }
}
