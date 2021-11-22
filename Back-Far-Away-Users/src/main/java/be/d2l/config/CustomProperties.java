package be.d2l.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "be.d2l")
public class CustomProperties {
    private String JWTSecret;
    public String getJWTSecret() {
        return JWTSecret;
    }
    public void setJWTSecret(String property) {
        this.JWTSecret = property;
    }
}
