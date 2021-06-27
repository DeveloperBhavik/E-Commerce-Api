package com.e_commerce_app.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.e_commerce_app.config.jwt.TokenAuthenticationFilter;
import com.e_commerce_app.exception_handler.AccessDeniedHandlerImplementation;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true,proxyTargetClass = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Token authentication filter
     * @return
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    
    /**
     * Bcrypt password encoder
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Authentication from auth controller
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    /**
     * Role and many other security configuration
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                    .exceptionHandling()
                    .accessDeniedHandler(new AccessDeniedHandlerImplementation())
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                    	"/resources/**","/error","/v2/api-docs/**","/static/**",
                        "/swagger.json","/api/public/logout",
                        "/favicon.ico","/**/*.png",
                        "/webjars/**","swagger-ui.html","/favicon.ico" ,
                        "/**/*.gif","/**/*.svg","/**/*.jpg","/**/*.png","/**/*.html","/**/*.css","/**/*.js")
                        .permitAll()
                    .antMatchers("/api/auth/**","/api/public/**").permitAll()
                    .antMatchers("/api/admin/**").access("hasAnyRole('ROLE_ADMIN')")
                    .antMatchers("/api/user/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .antMatchers("/api/cart/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .antMatchers("/api/category/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .antMatchers("/api/order/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .antMatchers("/api/order_detail/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .antMatchers("/api/product/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .antMatchers("/api/sub_category/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')");
        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}