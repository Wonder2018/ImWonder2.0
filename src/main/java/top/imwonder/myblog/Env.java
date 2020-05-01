package top.imwonder.myblog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties("env")
@Component
@Data
public class Env {
    
}