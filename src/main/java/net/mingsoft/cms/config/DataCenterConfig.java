package net.mingsoft.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 请求数据中心服务配置
 */
@Configuration
@ConfigurationProperties(prefix="datacenter.auth")
@Data
public class DataCenterConfig {

    private String host;

    private String trans;

    private String headerName;

    private String headerValue;

}
