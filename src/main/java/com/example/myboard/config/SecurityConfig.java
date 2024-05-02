package com.example.myboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean           //    @Bean 은 클래스 인스턴스이다
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request)->request
                        .requestMatchers("/css/**","/js/**","/images/**").permitAll()
//                        ** 하위에 있는 것들을 모두 포함
                        .requestMatchers("/user/**").permitAll()
//                        .requestMatchers("/**").permitAll())
                        .anyRequest().authenticated())

                .formLogin((form)->form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
//                        자동으로 시큐리티가 잡아채서 해결함으로써 getMapping 이 필요없다
//                        .usernameParameter("email")
//                        이메일로 로그인하는 방법은 여기 설정해주고 인터페이스에 쿼리메서드를 생성하여 서비스에서 끌어오는 방법?
                        .defaultSuccessUrl("/"))
                .logout((out)->out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout")
                );
        return http.build();
    }

}
