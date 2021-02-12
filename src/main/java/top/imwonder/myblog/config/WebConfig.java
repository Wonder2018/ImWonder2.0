/*
 * @Author: Wonder2019 
 * @Date: 2020-05-01 22:09:23 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-11 22:23:40
 */
package top.imwonder.myblog.config;

import com.google.gson.Gson;
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

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Env env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:/static/assets/robots.txt");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/assets/img/favicon.ico");
        registry.addResourceHandler("/sitemap.xml").addResourceLocations("file:./sitemap.xml");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }

    @Bean
    public ViewResolver beanNameViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        return resolver;
    }

    @Bean
    public View json() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public Auth auth() {
        return Auth.create(env.getOssAccessKey(), env.getOssSecretKey());
    }

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
