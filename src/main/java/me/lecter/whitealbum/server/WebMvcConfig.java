package me.lecter.whitealbum.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final String[] ALLOWED_ORIGINS = { "http://127.0.0.1:5500", "http://localhost:8100" };
	private final String[] ALLOWED_METHODS = { "GET", "POST", "PATCH", "PUT", "OPTION", "DELETE" };
	private final String[] ALLOWED_HEADERS = { "Content-Type", "varification_code" };

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(ALLOWED_ORIGINS).allowedMethods(ALLOWED_METHODS).allowedHeaders(ALLOWED_HEADERS);
	}
}
