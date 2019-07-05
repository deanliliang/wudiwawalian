package com.leyou.auth.task;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.mappers.ApplicationInfoMapper;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.bean.AppInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时获取token，保存token
 *
 * @author 虎哥
 */
@Slf4j
@Component
public class PrivilegeTokenHolder {

    @Autowired
    private JwtProperties prop;
    @Autowired
    private ApplicationInfoMapper infoMapper;

    private String token;

    /**
     * token刷新间隔
     */
    private static final long TOKEN_REFRESH_INTERVAL = 86400000L;

    @Scheduled(fixedDelay = TOKEN_REFRESH_INTERVAL)
    public void loadToken() throws InterruptedException {
        try {
            // 向ly-auth发起请求，获取JWT
            AppInfo appInfo = new AppInfo();
            appInfo.setId(prop.getApp().getId());
            appInfo.setServiceName(prop.getApp().getSecret());
            List<Long> longs = infoMapper.queryTargetIdListByAppId(prop.getApp().getId());
            appInfo.setTargetList(longs);
            this.token = JwtUtils.generateTokenExpireInMinutes(appInfo, prop.getPrivateKey(), prop.getApp().getExpire());
            log.info("【授权中心】定时获取token成功");
        } catch (Exception e) {
            log.info("【授权中心】定时获取token失败");
        }
    }
    public String getToken() {
        return token;
    }
}