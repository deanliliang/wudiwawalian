package com.leyou.user.controller;

import com.leyou.user.bean.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.user.controller
 * @ClassName: UserController
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 20:17
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 验证用户名 手机号是否唯一
     *
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data,
                                             @PathVariable("type") Integer type) {

        return ResponseEntity.ok(userService.checkUser(data, type));
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<HttpStatus> saveUser(User user,
                                         @RequestParam("code") String code
    ) {
        System.out.println(1);
        userService.saveUser(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 发送短信验证码
     *
     * @return
     */
    @PostMapping("/code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) {
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 登陆
     *Todo    先不做
     *Todo    先不做
     * @return
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@RequestParam("username")String username,
                                      @RequestParam("password")String password) {

        User user = new User();
        userService.login(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
