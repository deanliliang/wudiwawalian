package com.leyou;

import com.leyou.user.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ProjectName: leyou
 * @Package: com.leyou
 * @ClassName: UserApplication
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 19:59
 * @Version: 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.leyou.user.mapper")
@EnableConfigurationProperties(JwtProperties.class)
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

}
