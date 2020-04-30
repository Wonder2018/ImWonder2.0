package top.imwonder.myblog.config;

import java.util.HashMap;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import top.imwonder.myblog.login.WonderFormAuthenticationFilter;

/**
 * Created by qh on 2017/4/9.
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> fb = new FilterRegistrationBean<XssFilter>();
        fb.setFilter(new XssFilter());
        fb.setOrder(1);
        fb.setEnabled(true);
        fb.addUrlPatterns("/*");
        HashMap<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", "/favicon.ico,/assets/.*");
        initParameters.put("isIncludeRichText", "true");
        fb.setInitParameters(initParameters);
        return fb;
    }

    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> shiroFilter(){
        DelegatingFilterProxy filter = new DelegatingFilterProxy();
        FilterRegistrationBean<DelegatingFilterProxy> fb = new FilterRegistrationBean<DelegatingFilterProxy>();
        fb.addInitParameter("targetFilterLifecycle", "true");
        fb.addUrlPatterns("/*");
        fb.setFilter(filter);
        return fb;
    }

    @Bean
    public Filter authcWonder(){
        WonderFormAuthenticationFilter filter = new WonderFormAuthenticationFilter();
        filter.setRoleBase("admin");
        filter.setLoginUrl("/wonderlandsadmin/login");
        filter.setSuccessUrl("/wonderlandsadmin/index.html");
        return filter;
    }

    @Bean
    public Filter authcUsr(){
        WonderFormAuthenticationFilter filter = new WonderFormAuthenticationFilter();
        filter.setRoleBase("poster");
        filter.setLoginUrl("/wonderpost/login");
        filter.setSuccessUrl("/wonderpost/index.html");
        return filter;
    }
}
