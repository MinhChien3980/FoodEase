package com.foodease.myapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiKeyProperties {
    /**
     * Public API key client gửi lên
     */
    private String key;
    /**
     * Secret dùng để verify hoặc ký request
     */
    private String secret;
}
