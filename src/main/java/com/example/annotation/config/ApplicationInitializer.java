package com.example.annotation.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class ApplicationInitializer implements WebApplicationInitializer {
	/**
	 * 用于注册监听器，过滤器 相当于之前的web.xml
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {
		FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding",
				CharacterEncodingFilter.class);
		characterEncoding.setInitParameter("forceEncoding", "true");
		characterEncoding.setInitParameter("encoding", "UTF-8");
		characterEncoding.addMappingForUrlPatterns(null, true, "/*");
		servletContext.addListener(RequestContextListener.class);
	}

}
