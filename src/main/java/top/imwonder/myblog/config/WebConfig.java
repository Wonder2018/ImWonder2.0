/*
 * @Author: Wonder2019 
 * @Date: 2020-05-01 22:09:23 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-16 13:00:21
 */
package top.imwonder.myblog.config;

import com.google.gson.Gson;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import top.imwonder.myblog.Env;
import top.imwonder.myblog.enumeration.EnumConverterFactory;
import top.touchface.md2x.Md2x;
import top.touchface.md2x.entity.Options;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Env env;

    // 处理静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String asstesRoot = env.getAssetsRoot().getAbsolutePath();
        System.out.println("file:" + asstesRoot);
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/blog-assets/**").addResourceLocations("file:" + asstesRoot + "/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:/static/assets/robots.txt");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/assets/img/favicon.ico");
        registry.addResourceHandler("/sitemap.xml").addResourceLocations("file:./sitemap.xml");
    }

    // 枚举转换器
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }

    // 视图解析器
    @Bean
    public ViewResolver beanNameViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        return resolver;
    }

    // json视图
    @Bean
    public View json() {
        return new MappingJackson2JsonView();
    }

    // gson工具
    @Bean
    public Gson gson() {
        return new Gson();
    }

    // md2x工具
    @Bean
    public Md2x md2x() {
        return new Md2x(new Options());
    }

    // 七牛云认证
    @Bean
    public Auth auth(Env env) {
        return Auth.create(env.getOssAccessKey(), env.getOssSecretKey());
    }

    // 七牛云配置类
    @Bean
    public com.qiniu.storage.Configuration qiniuConfiguration() {
        return new com.qiniu.storage.Configuration(Region.region2());
    }

    // 七牛云桶管理器
    @Bean
    public BucketManager bucketManager(Auth auth, com.qiniu.storage.Configuration cfg) {
        return new BucketManager(auth, cfg);
    }

    // 七牛云上传管理器
    @Bean
    public UploadManager uploadManager(com.qiniu.storage.Configuration cfg) {
        return new UploadManager(cfg);
    }

    // 通用http客户端
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // @Bean
    // public ServletListenerRegistrationBean<HttpSessionListener>
    // sessionListener(UsageHttpSessionListener usageHttpSessionListener) {
    // return new
    // ServletListenerRegistrationBean<HttpSessionListener>(usageHttpSessionListener);
    // }

}
