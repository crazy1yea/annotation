package com.example.annotation.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ConfigInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	/**
	 * 根容器配置类（Spring配置）
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {RootConfig.class};
	}
	/**
	 * WEB、相关配置（SpringMVC配置）
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {ServletConfig.class};
	}
	/**
	 * DispatcherServlet的前端映射
	 */
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		// 拦截所有请求不包括jsp
		return new String[] {"/"};
	}
}
