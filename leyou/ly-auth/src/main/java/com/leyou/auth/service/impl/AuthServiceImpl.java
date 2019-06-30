package com.leyou.auth.service.impl;

import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.user.client.UserClient;
import com.leyou.user.pojo.dto.UserDTO;
import com.leyou.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.auth.service.impl
 * @ClassName: AuthServiceImpl
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/29 23:57
 * @Version: 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private UserClient userClient;

    private static final String USER_ROLE = "role_user";

    /**
     * 用户登录
     * @param username
     * @param password
     * @param response
     */
    @Override
    public void login(String username, String password, HttpServletResponse response) {


        try {
            // 查询用户
            UserDTO user = userClient.queryUserByUsernameAndPassword(username, password);
            // 生成userInfo, 没写权限功能，暂时都用guest
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), USER_ROLE);
            // 生成token
            String token = JwtUtils.generateTokenExpireInMinutes(userInfo, prop.getPrivateKey(), prop.getUser().getExpire());
            // 写入cookie
            CookieUtils.newCookieBuilder()
                    .response(response) // response,用于写cookie
                    .httpOnly(true) // 保证安全防止XSS攻击，不允许JS操作cookie
                    .domain(prop.getUser().getCookieDomain()) // 设置domain
                    .name(prop.getUser().getCookieName()).value(token) // 设置cookie名称和值
                    .build();// 写cookie
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
    }
}
