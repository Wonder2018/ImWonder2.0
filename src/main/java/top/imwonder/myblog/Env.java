package top.imwonder.myblog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("env")
public class Env {
    
    public String ossProtocol;
    
    public String ossHostSuffix;

    public String ossAccessKey;

    public String ossSecretKey;
    
    // public File resource;

    // public void setResource(File resource){
    //     if(!resource.exists()){
    //         resource.mkdirs();
    //     }
    //     this.resource = resource;
    // }
}