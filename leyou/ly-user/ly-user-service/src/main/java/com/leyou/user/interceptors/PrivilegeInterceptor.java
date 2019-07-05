package com.leyou.user.interceptors;

import com.leyou.auth.bean.Payload;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.bean.AppInfo;
import com.leyou.user.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 校验请求头中的token
 * @author 虎哥
 */
@Slf4j
public class PrivilegeInterceptor implements HandlerInterceptor {

    private JwtProperties jwtProp;

    public PrivilegeInterceptor(JwtProperties jwtProp) {
        this.jwtProp = jwtProp;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // 获取请求头
            String headerName = jwtProp.getApp().getHeaderName();
            String token = request.getHeader(jwtProp.getApp().getHeaderName());
            // 校验
            Payload<AppInfo> payload = JwtUtils.getInfoFromToken(token, jwtProp.getPublicKey(), AppInfo.class);
            // 获取token中的服务信息,
            AppInfo appInfo = payload.getUserInfo();
            // 验证是否有访问本服务的许可
            List<Long> targetList = appInfo.getTargetList();
            Long currentServiceId = jwtProp.getApp().getId();
            if(targetList == null || !targetList.contains(currentServiceId)){
                // 没有访问权限，抛出异常
                throw new RuntimeException("请求者没有访问本服务的权限！");
            }
            log.info("服务{}正在请求资源:{}", appInfo.getServiceName(), request.getRequestURI());
            return true;
        }catch (Exception e){
            log.error("服务访问被拒绝,token认证失败!", e);
            return false;
        }
    }
}