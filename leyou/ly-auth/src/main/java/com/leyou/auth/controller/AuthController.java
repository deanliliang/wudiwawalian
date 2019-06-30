package com.leyou.auth.controller;

import com.leyou.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        authService.login(username,password,response);
        return ResponseEntity.ok().build();
    }
}
