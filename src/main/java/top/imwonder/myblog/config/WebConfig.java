/*
 * @Author: Wonder2019 
 * @Date: 2020-05-01 22:09:23 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-02 19:10:17
 */
package top.imwonder.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
@EnableWebMvc
@ImportResource({ "classpath:applicationContext.xml" })
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/assets/img/favicon.ico");
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
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    // @Bean
    // public ServletListenerRegistrationBean<HttpSessionListener>
    // sessionListener(UsageHttpSessionListener usageHttpSessionListener) {
    // return new
    // ServletListenerRegistrationBean<HttpSessionListener>(usageHttpSessionListener);
    // }

}
