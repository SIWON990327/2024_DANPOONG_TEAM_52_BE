package com.groom.orbit.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final String[] allowedUrls = {"/api", "api/auth/kakao", "/kakao/**"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.formLogin(AbstractHttpConfigurer::disable);

    http.httpBasic(AbstractHttpConfigurer::disable);

    http.csrf(AbstractHttpConfigurer::disable);

    http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    http.sessionManagement(
        sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(
        (authorize) ->
            authorize.requestMatchers(allowedUrls).permitAll().anyRequest().authenticated());

    return http.build();
  }
}
