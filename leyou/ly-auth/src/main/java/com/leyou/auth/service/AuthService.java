package com.leyou.auth.service;

import com.leyou.auth.bean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.auth.service
 * @ClassName: AuthService
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/29 23:57
 * @Version: 1.0
 */
public interface AuthService {


    void login(String username, String password, HttpServletResponse response);

    UserInfo getVerify(HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    String authServiceByAppInfo(Long id, String secret);
}
