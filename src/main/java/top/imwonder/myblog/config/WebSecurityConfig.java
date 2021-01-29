/*
 * @Author: Wonder2020 
 * @Date: 2021-01-22 15:49:18 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-29 11:46:12
 */
package top.imwonder.myblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService wuds;

    @Autowired
    private AccessDeniedHandler adh;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(wuds).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(adh);
        http.formLogin().loginPage("/wonderlandsadmin/login").loginProcessingUrl("/wonderadmin/login")
                .defaultSuccessUrl("/wonderlandsadmin/index").permitAll();
        http.logout().logoutUrl("/wonderlandsadmin/logout").logoutSuccessUrl("/");
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests().antMatchers("/wonderlandsadmin/**").authenticated();
        http.authorizeRequests().antMatchers("/assets/**/admin/**").authenticated();
        http.authorizeRequests().antMatchers("/**").permitAll();
    }
}
