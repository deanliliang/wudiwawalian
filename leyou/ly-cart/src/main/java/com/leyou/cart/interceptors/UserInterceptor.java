package com.leyou.cart.interceptors;

import com.leyou.auth.bean.Payload;
import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.exception.LyException;
import com.leyou.threadlocals.UserHolder;
import com.leyou.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.cart.interceptors
 * @ClassName: UserInterceptor
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 17:30
 * @Version: 1.0
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    private static final String COOKIE_NAME = "LY_TOKEN";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
// 获取请求中的token
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        try {
            // 解析token中的用户
            Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, UserInfo.class);
            // 存储用户
            UserHolder.setUser(payload.getUserInfo());
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("解析用户token失败，原因：{}", e);
            throw new LyException(500, "解析用户token失败", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        UserHolder.remove();
    }
}
