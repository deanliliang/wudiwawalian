package cn.dean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ProjectName: cloud-demo
 * @Package: cn.dean
 * @ClassName: EurekaServer
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/9 21:55
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaServer // 声明这个应用是一个EurekaServer
//@MapperScan("cn.dean.mapper")
public class EurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }
}
