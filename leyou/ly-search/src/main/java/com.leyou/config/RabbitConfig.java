package com.leyou.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.config
 * @ClassName: RabbitConfig
 * @Author: Dean
 * @Description: 自定义转换器
 * @Date: 2019/6/26 10:02
 * @Version: 1.0
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
