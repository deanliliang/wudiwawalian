package com.leyou.gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.gateway.config
 * @ClassName: GlobalCORSConfig
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/15 2:23
 * @Version: 1.0
 */
/**
 * 配置类，配置CORS拦截
 */
@Configuration
@EnableConfigurationProperties(CORSProperties.class)
public class GlobalCORSConfig {
    @Bean
    public CorsFilter corsFilter(CORSProperties prop) {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了

        /*List<String> allowedOrigins = prop.getAllowedOrigins();
        for (String allowedOrigin : allowedOrigins) {
            config.addAllowedOrigin(allowedOrigin);
        }*/
        prop.getAllowedOrigins().forEach(config::addAllowedOrigin);

        //2) 是否发送Cookie信息
        config.setAllowCredentials(prop.getAllowedCredentials());
        //3) 允许的请求方式
        prop.getAllowedMethods().forEach(config::addAllowedMethod);
        // 4）允许的头信息
        prop.getAllowedHeaders().forEach(config::addAllowedHeader);
        // 5）有效期
        config.setMaxAge(prop.getMaxAge());

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration(prop.getFilterPath(), config);
        System.out.println(config.getMaxAge());
        //3.返回新的CORSFilter.
        return new CorsFilter(configSource);
    }
}