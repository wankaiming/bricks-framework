package org.bricks.framework.common.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getXssInterceptor()).addPathPatterns("/**");
	}

	@Bean
	public XssInterceptor getXssInterceptor() {
		return new XssInterceptor();
	}
}
