package com.leyou.auth.config;

import com.leyou.auth.task.PrivilegeTokenHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 虎哥
 */
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor(
            JwtProperties jwtProp, PrivilegeTokenHolder tokenHolder) {
        return new PrivilegeInterceptor(jwtProp, tokenHolder);
    }

    private class PrivilegeInterceptor implements RequestInterceptor {

        private JwtProperties jwtProp;

        private PrivilegeTokenHolder tokenHolder;

        public PrivilegeInterceptor(JwtProperties jwtProp, PrivilegeTokenHolder tokenHolder) {
            this.jwtProp = jwtProp;
            this.tokenHolder = tokenHolder;
        }

        @Override
        public void apply(RequestTemplate template) {
            template.header(jwtProp.getApp().getHeaderName(), tokenHolder.getToken());
        }
    }
}