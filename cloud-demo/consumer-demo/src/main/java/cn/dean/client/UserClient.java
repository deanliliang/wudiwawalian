package cn.dean.client;

import cn.dean.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ProjectName: cloud-demo
 * @Package: cn.dean.client
 * @ClassName: UserClient
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/10 22:51
 * @Version: 1.0
 */

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/user/{id}")
    User queryById(@PathVariable("id") Long id);
}
