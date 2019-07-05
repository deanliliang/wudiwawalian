package com.leyou.auth.service.impl;

import com.leyou.auth.bean.ApplicationInfo;
import com.leyou.auth.bean.Payload;
import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.mappers.ApplicationInfoMapper;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.bean.AppInfo;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.user.client.UserClient;
import com.leyou.user.pojo.dto.UserDTO;
import com.leyou.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserClient userClient;

    private static final String USER_ROLE = "role_user";

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param response
     */
    @Override
    public void login(String username, String password, HttpServletResponse response) {


        try {
            // 查询用户
            UserDTO user = userClient.queryUserByUsernameAndPassword(username, password);
            // 2、判断查询是否存在
            if (user == null) {
                // 不存在：用户名或密码错误，
                throw new RuntimeException();
            }
            // 生成userInfo, 没写权限功能，暂时都用guest
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), USER_ROLE);
            // 生成token
            String token = JwtUtils.generateTokenExpireInMinutes(userInfo, prop.getPrivateKey(), prop.getUser().getExpire());
            // 写入cookie
            CookieUtils.newCookieBuilder().name(prop.getUser().getCookieName()).value(token) // 设置cookie名称和值
                    .domain(prop.getUser().getCookieDomain())  // 设置domain
                    .httpOnly(true) // 保证安全防止XSS攻击，不允许JS操作cookie
                    .response(response) // response,用于写cookie
                    .build(); // 写cookie
            log.info("【授权中心】{}登录成功", user.getUsername());
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
    }

    /**
     * 判断用户登录状态
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public UserInfo getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
//        得到token
            String token = CookieUtils.getCookieValue(request, prop.getUser().getCookieName());
//        获取token你的用户信息 存在则处于登陆状态
            Payload<UserInfo> info = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), UserInfo.class);
//            获取token id如果存在于redis也是登陆失效
            String tokenId = info.getId();
            Boolean aBoolean = redisTemplate.hasKey(tokenId);
            if (aBoolean != null && aBoolean) {
//                抛出异常证明token失效
                throw new LyException(ExceptionEnum.UNAUTHORIZED);
            }
//          刷新token
            long l = info.getExpiration().getTime() - System.currentTimeMillis();
            if (l < prop.getUser().getMinRefreshInterval()) {
                JwtUtils.generateTokenExpireInMinutes(info.getUserInfo(),
                        prop.getPrivateKey(), prop.getUser().getExpire());

                // 写入cookie
                CookieUtils.newCookieBuilder()
                        // response,用于写cookie
                        .response(response)
                        // 保证安全防止XSS攻击，不允许JS操作cookie
                        .httpOnly(true)
                        // 设置domain
                        .domain(prop.getUser().getCookieDomain())
                        // 设置cookie名称和值
                        .name(prop.getUser().getCookieName()).value(token)
                        // 写cookie
                        .build();
            }

            return info.getUserInfo();
        } catch (Exception e) {
            // 抛出异常，证明token无效，直接返回401
            log.error("【授权中心】用户token无效。");
            throw new LyException(ExceptionEnum.UNAUTHORIZED);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        获取token
        String token = CookieUtils.getCookieValue(request, prop.getUser().getCookieName());
//        解析token
        Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), UserInfo.class);
        // 获取id和有效期剩余时长
        String payloadId = payload.getId();
        long time = payload.getExpiration().getTime();
//        写入ridis加入黑名单
        redisTemplate.opsForValue().set(payloadId, "", time, TimeUnit.MILLISECONDS);
//        删除cookie
        CookieUtils.deleteCookie(prop.getUser().getCookieName(), prop.getUser().getCookieDomain(), response);
    }


    @Autowired
    private ApplicationInfoMapper infoMapper;

    @Autowired
    private BCryptPasswordEncoder passwordConfig;

    /**
     * 微服务认证并申请令牌
     *
     * @param id
     * @param secret
     * @return
     */
    @Override
    public String authServiceByAppInfo(Long id, String secret) {
//        根据id查询appinfo
        ApplicationInfo applicationInfo = infoMapper.selectByPrimaryKey(id);
        if (applicationInfo == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
//        验证密码
        boolean matches = passwordConfig.matches(secret, applicationInfo.getSecret());
        if (!matches) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

//        查询服务权限集合信息
        List<Long> longs = infoMapper.queryTargetIdListByAppId(id);
//        封装info信息
        AppInfo appInfo = new AppInfo();
        appInfo.setId(id);
        appInfo.setServiceName(applicationInfo.getServiceName());
        appInfo.setTargetList(longs);

//        生成jwt
        String token = JwtUtils.generateTokenExpireInMinutes(appInfo, prop.getPrivateKey(), prop.getApp().getExpire());

        return token;
    }
}
