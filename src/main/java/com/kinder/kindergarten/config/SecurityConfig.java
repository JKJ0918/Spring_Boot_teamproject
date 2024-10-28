package com.kinder.kindergarten.config;

import com.kinder.kindergarten.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    @Bean  // 패스워드를 db에 저장할 때 암호화 처리함.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .formLogin(form -> {
                    form
                    .loginPage("/employee/login")    // 로그인 페이지
                    .defaultSuccessUrl("/", true)          // 로그인 성공시 기본 경로
                    .usernameParameter("email")      // 로그인시 인증 키값
                    .failureUrl("/employee/login/error");  // 로그인 실패시 갈 경로
        })
                .logout(logout ->{
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/employee/logout")) // 로그아웃 처리용 경로
                    .logoutSuccessUrl("/employee/login");     // 로그아웃 성공시 갈 경로
        });

        return http.build();
    }
}
