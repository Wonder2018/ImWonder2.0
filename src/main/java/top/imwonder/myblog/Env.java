package top.imwonder.myblog;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("env")
public class Env {
    
    private String ossProtocol;
    
    private String ossHostSuffix;

    private String ossAccessKey;

    private String ossSecretKey;
    
    private Long ossExpireInSeconds;

    private Long resourceExpireInSeconds;

    private File articleRoot;
    
    // public File resource;

    // public void setResource(File resource){
    //     if(!resource.exists()){
    //         resource.mkdirs();
    //     }
    //     this.resource = resource;
    // }
}