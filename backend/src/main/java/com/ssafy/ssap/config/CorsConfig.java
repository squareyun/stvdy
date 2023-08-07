package com.ssafy.ssap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		source.registerCorsConfiguration("/users/**", config);

		// Create a new CORS configuration for "/mypage/**" pattern
		CorsConfiguration myPageConfig = new CorsConfiguration();
		myPageConfig.setAllowCredentials(true);
		myPageConfig.addAllowedOriginPattern("*");
		myPageConfig.addAllowedHeader("*");
		myPageConfig.addAllowedMethod("*");

		// Register CORS configuration for "/mypage/**" pattern
		source.registerCorsConfiguration("/mypage/**", myPageConfig);

		return new CorsFilter(source);
	}
}
