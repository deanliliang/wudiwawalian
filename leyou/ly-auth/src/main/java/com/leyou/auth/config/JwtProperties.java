package com.leyou.auth.config;

import com.leyou.auth.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.auth.config
 * @ClassName: JwtProperties
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/29 22:53
 * @Version: 1.0
 */

@Data
@Slf4j
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties implements InitializingBean {
    /**
     * 公钥地址
     */
    private String pubKeyPath;
    /**
     * 私钥地址
     */
    private String priKeyPath;

    /**
     * 用户token相关属性
     */
    private UserTokenProperties user = new UserTokenProperties();

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Data
    public class UserTokenProperties {
        /**
         * token过期时长
         */
        private int expire;
        /**
         * 存放token的cookie名称
         */
        private String cookieName;
        /**
         * 存放token的cookie的domain
         */
        private String cookieDomain;
        /**
         * 最小刷新时间
         */
        private long minRefreshInterval;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException("加载公钥和私钥异常.", e);
        }
    }
}
