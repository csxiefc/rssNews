package net.mingsoft.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("client.auth")
public class ClientAuthConfig {

    private String headerName;

    private String headerValue;

    private String includeReferer;
}
