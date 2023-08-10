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

		source.registerCorsConfiguration("/**", config);

		//스터디룸 관련 CORS설정
		CorsConfiguration roomsConfig = new CorsConfiguration();
		roomsConfig.setAllowCredentials(true);
		roomsConfig.addAllowedOriginPattern("*");
		roomsConfig.addAllowedHeader("*");
		roomsConfig.addAllowedMethod("*");

		source.registerCorsConfiguration("/rooms/**",roomsConfig);

		return new CorsFilter(source);
	}
}
