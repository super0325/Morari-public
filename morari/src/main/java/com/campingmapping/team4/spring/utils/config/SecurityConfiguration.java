package com.campingmapping.team4.spring.utils.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.campingmapping.team4.spring.utils.service.LogoutSuccessHandlerImpl;
import com.campingmapping.team4.spring.utils.service.OAuth2AuthSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    // @Autowired
    // private final OAuth2UserService<OAuth2UserRequest, OAuth2User>
    // oAuthUserService;
    @Autowired
    private final OAuth2AuthSuccessHandler  successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            http
                    // 關閉CSRF
                    .csrf().disable()
                //     Frame 同源設定
                    .headers(h->h
                    .frameOptions().sameOrigin())
                    // 設定是否需要驗證的路徑(更改成使用註釋)
                    .authorizeHttpRequests(a -> a
                            .requestMatchers("/admin").hasAnyAuthority("SUPERADMIN")
                            .anyRequest().permitAll()
                            )
                    // 啟用jwt監聽
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    // 登入頁面
                    .formLogin(formLogin -> formLogin
                            .loginPage("/login")
                            .permitAll())
                    // OAuth2登入
                    .oauth2Login(o -> o
                            .loginPage("/login")
                            .authorizationEndpoint(auth -> auth.baseUri("/login/oauth2/authorization"))
                            .successHandler(successHandler))
                    // 登出頁面
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessHandler(logoutSuccessHandler)
                            .logoutSuccessUrl("/")
                            .permitAll())
                    // 若無權限指定路徑
                    // .exceptionHandling(exceptionHandling -> {
                    // System.out.println("88");
                    // exceptionHandling.accessDeniedPage("/home");
                    // })
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http.build();
        } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

}
