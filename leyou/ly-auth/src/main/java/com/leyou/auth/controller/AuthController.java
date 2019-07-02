package com.leyou.auth.controller;

import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.auth.controller
 * @ClassName: AuthController
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/29 23:52
 * @Version: 1.0
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam("username")String username,
                                      @RequestParam("password")String password,
                                      HttpServletResponse response){

        System.out.println(1);
        authService.login(username,password,response);
        return ResponseEntity.ok().build();
    }

    /**
     * 判断用户登录状态
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/verify")
    public ResponseEntity<UserInfo> getVerify(HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.ok(authService.getVerify(request,response));
    }

    /**
     * 用户退出
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        authService.logout(request,response);
        return ResponseEntity.ok().build();
    }
}
