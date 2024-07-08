package com.example.userservice.security;


import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserRepository userRepository;
    private final Environment env;
    private final JwtProvider jwtProvider;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher( "/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher( "/css/**"))
                .requestMatchers(new AntPathRequestMatcher( "/js/**"))
                .requestMatchers(new AntPathRequestMatcher( "/img/**"))
                .requestMatchers(new AntPathRequestMatcher( "/lib/**"));
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(new MvcRequestMatcher(introspector, "/**")).permitAll()
                                 .requestMatchers(new MvcRequestMatcher(introspector, "/users/**")).permitAll()
                                 .requestMatchers(new MvcRequestMatcher(introspector, "/actuator/**")).permitAll()
//                                requestMatchers(new MvcRequestMatcher.Builder(introspector).pattern(HttpMethod.GET, "/users/**")).permitAll()
//                                .requestMatchers(new MvcRequestMatcher(introspector, "/greeting")).permitAll()
//                                .requestMatchers(new MvcRequestMatcher(introspector, "/welcome")).permitAll()
//                                .requestMatchers(new MvcRequestMatcher(introspector, "/health-check")).permitAll()
//                                .requestMatchers(new MvcRequestMatcher.Builder(introspector).pattern(HttpMethod.POST, "/users")).permitAll()
                                .anyRequest()
                                .authenticated())
                .addFilter(getAuthenticationFilter())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    private AuthenticationFilter getAuthenticationFilter() {
        return new AuthenticationFilter(customAuthenticationManager, userRepository, env, jwtProvider);
    }


}
