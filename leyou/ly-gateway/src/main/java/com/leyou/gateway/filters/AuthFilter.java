package com.leyou.gateway.filters;

import com.leyou.auth.bean.Payload;
import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.leyou.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.gateway.filters
 * @ClassName: AuthFilter
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/1 21:19
 * @Version: 1.0
 */
@Slf4j
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FilterProperties filterProps;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
//        获取request
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        return !isAllowPath(requestURI);
//        return false;
    }

    private boolean isAllowPath(String requestURI) {
        boolean flag = false;
        for (String path : filterProps.getAllowPaths()) {
            if (path.startsWith(requestURI)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Object run() throws ZuulException {
        // 1.获取Request
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 2.获取用户的登录凭证jwt
        String token = CookieUtils.getCookieValue(request, jwtProperties.getUser().getCookieName());
        try {
            // 3.验证用户是否登录
            // 3.1.解析jwt，获取用户身份
            Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey(), UserInfo.class);
            // 4.1.根据身份，查询用户权限信息
            UserInfo userInfo = payload.getUserInfo();
            String role = userInfo.getRole();
            // 4.2.获取当前请求资源（微服务接口路径）
            String URI = request.getRequestURI();
            String method = request.getMethod();

            // TODO 用户权限判断

            // 4.3.判断是否有访问资源的权限
            log.info("【网关】用户{}，角色{}，正在访问{}: {}",
                    userInfo.getUsername(), userInfo.getRole(), method, URI);
        } catch (RuntimeException e) {
            // 拦截请求
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
        }

        return null;
    }
}
