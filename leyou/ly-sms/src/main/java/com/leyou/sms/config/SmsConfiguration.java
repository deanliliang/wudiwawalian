package com.leyou.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.sms.config
 * @ClassName: SmsConfiguration
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 15:31
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsConfiguration {

    @Bean
    public IAcsClient acsClient(SmsProperties prop) {
        DefaultProfile profile = DefaultProfile.getProfile(prop.getRegionID(),prop.accessKeyID, prop.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

}
