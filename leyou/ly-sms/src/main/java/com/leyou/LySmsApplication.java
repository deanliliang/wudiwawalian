package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ProjectName: leyou
 * @Package: com.leyou
 * @ClassName: LySmsApplication
 * @Author: Dean
 * @Description: 阿里短信服务启动类
 * @Date: 2019/6/26 22:20
 * @Version: 1.0
 */

@SpringBootApplication
public class LySmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LySmsApplication.class,args);
    }
}
