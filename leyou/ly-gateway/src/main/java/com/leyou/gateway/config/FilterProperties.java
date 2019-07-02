package com.leyou.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "ly.filter")
public class FilterProperties {

    /**
     * 访问路径白名单
     */
    private List<String> allowPaths;

}