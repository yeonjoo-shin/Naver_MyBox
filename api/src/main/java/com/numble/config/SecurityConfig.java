package com.numble.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()           // csrf 보안 필요없으므로 disable 처리

                .exceptionHandling()
//                .authenticationEntryPoint()
//                .accessDeniedHandler()

                // h2 console을 위한 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // security는 기본적으로 세션을 사용
                // 세션 설정을 사용하지 않기 때문에 세션 설정을 Stateless로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers().permitAll()
                .anyRequest()
                .authenticated()

                // JWTFilter를  addFilterBefore로 등록했던  JwtSecurityConfig 클래스 적용
                .and()
                .apply(new JwtSecurityConfig());
    }
}
