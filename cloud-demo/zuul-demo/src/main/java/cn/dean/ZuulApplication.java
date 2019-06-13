package cn.dean;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ProjectName: cloud-demo
 * @Package: cn.dean
 * @ClassName: ZuulApplication
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/12 22:58
 * @Version: 1.0
 */

@SpringBootApplication
@EnableZuulProxy  // 开启Zuul的网关功能
@EnableDiscoveryClient
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class,args);
    }

}
