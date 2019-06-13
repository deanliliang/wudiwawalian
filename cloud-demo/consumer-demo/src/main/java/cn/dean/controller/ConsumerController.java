package cn.dean.controller;

import cn.dean.bean.User;
import cn.dean.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ProjectName: cloud-demo
 * @Package: cn.dean.controller
 * @ClassName: ConsumerController
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/9 20:34
 * @Version: 1.0
 */
@RestController
@RequestMapping("consumer")
public class ConsumerController {

//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private UserClient userClient;



    /**
     * feign写法
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public User queryById(@PathVariable("id") Long id){
        return userClient.queryById(id);
    }


////    第一种写法
//    @GetMapping("{id}")
//    public User queryById(@PathVariable("id") Long id){
////        String url = "http://localhost:8081/user/" + id;
////        User user = restTemplate.getForObject(url, User.class);
//        // 根据服务id(spring.application.name)，获取服务实例列表
//        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
//        // 取出一个服务实例
//        ServiceInstance instance = instances.get(0);
//        // 从实例中获取host和port，组成url
//        String url = String.format("http://%s:%d/user/%d", instance.getHost(), instance.getPort(), id);
//        User user = restTemplate.getForObject(url, User.class);
//
//        return user;
//    }



    //    第二种写法
//    @GetMapping("{id}")
//    public User queryById1(@PathVariable("id") Long id){
////        根据user-service查找对应服务
//        String url = "http://user-service/user/"+id;
//        User user = restTemplate.getForObject(url, User.class);
//        return user;
//    }

}
