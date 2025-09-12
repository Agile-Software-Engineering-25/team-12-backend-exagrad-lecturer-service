package com.ase.lecturerservice.config;

import java.util.Arrays;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsConfig.CorsConfigurationProperties.class)
public class CorsConfig {

  private final CorsConfigurationProperties corsConfigurationProperties;

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.setAllowCredentials(true);
    Arrays.stream(corsConfigurationProperties.allowedOrigins).forEach(corsConfiguration::addAllowedOrigin);
    log.info("Allowed origins:");
    Arrays.stream(corsConfigurationProperties.allowedOrigins).forEach(System.out::println);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  CorsFilter corsFilter() {
    return new CorsFilter(corsConfigurationSource());
  }

  @ConfigurationProperties(prefix = "app.cors")
  record CorsConfigurationProperties(String[] allowedOrigins) {

  }
}
