package com.study.studyolle.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/login", "/sign-up",
                        "/check-email-token", "/email-login",
                        "/check-email-login", "/login-link") // 해당 링크는 권한체크 패스
                .permitAll()
                .mvcMatchers(HttpMethod.GET, "/profile/*") // 프로필 관련 URL은 Get만 허용
                .permitAll()
                .anyRequest() // 위에 항목을 제외한 나머지 Request는 전부 권한체크
                .authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.logout()
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**") // static node_modules/** 들은 시큐리티 미적용
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // static resource 들은 시큐리티 미적용
    }
}
