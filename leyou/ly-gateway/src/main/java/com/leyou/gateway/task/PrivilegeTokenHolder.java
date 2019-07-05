package com.leyou.gateway.task;

import com.leyou.gateway.client.AuthClient;
import com.leyou.gateway.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时获取token，保存token
 * @author 虎哥
 */
@Slf4j
@Component
public class PrivilegeTokenHolder {

    @Autowired
    private JwtProperties prop;

    private String token;

    /**
     * token刷新间隔
     */
    private static final long TOKEN_REFRESH_INTERVAL = 86400000L;

    /**
     * token获取失败后重试的间隔
     */
    private static final long TOKEN_RETRY_INTERVAL = 10000L;

    @Autowired
    private AuthClient authClient;

    @Scheduled(fixedDelay = TOKEN_REFRESH_INTERVAL)
    public void loadToken() throws InterruptedException {
        while (true) {
            try {
                // 向ly-auth发起请求，获取JWT
                this.token = authClient.authServiceByAppInfo(prop.getApp().getId(), prop.getApp().getSecret());
                log.info("【网关】定时获取token成功");
                break;
            } catch (Exception e) {
                log.info("【网关】定时获取token失败");
            }
            // 休眠10秒，再次重试
            Thread.sleep(TOKEN_RETRY_INTERVAL);
        }
    }

    public String getToken(){
        return token;
    }
}