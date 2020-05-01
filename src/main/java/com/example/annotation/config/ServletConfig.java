package com.example.annotation.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@ComponentScan(value = "com.example.annotation", includeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) }, useDefaultFilters = false)
@Import({ MultipartResolverConfig.class })
public class ServletConfig implements WebMvcConfigurer {
	/*
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setPrefix("/html/");
		viewResolver.setSuffix(".html");
		viewResolver.setCache(true);
		viewResolver.setContentType("text/html;charset=UTF-8");
		viewResolver.setRequestContextAttribute("requestContext"); // freemarker提供的获取项目上下文的对象
		viewResolver.setOrder(0);
		viewResolver.setViewClass(MyFreeMarkerView.class); // 将自定义的的扩展的视图解析器加入freemaker管理中
		return viewResolver;
	}

	@Bean
	public FreeMarkerConfig freeMarkerConfig() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		freemarker.template.Configuration configuration = new freemarker.template.Configuration();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		configuration.setTagSyntax(freemarker.template.Configuration.AUTO_DETECT_TAG_SYNTAX);
		configuration.setTemplateUpdateDelay(1);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setOutputEncoding("UTF-8");
		configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
		configuration.setDateFormat("yyyy-MM-dd");
		configuration.setTimeFormat("HH:mm:ss");
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		configuration.setClassicCompatible(true);
		configuration.setTemplateLoader(new WebappTemplateLoader(servletContext));

		configurer.setConfiguration(configuration);

		return configurer;
	}

	// 配置静态资源的处理
	// 使DispatcherServlet对静态资源的请求转发到Servlet容器默认的Servlet上，而不是使用DispatcherServlet本身来处理此类请求
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	*//**
	 * 将自定义的拦截器加入springmvc的配置管理中 date:2018年12月25日
	 *//*
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(basePathInterceptor);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(fastConverter);
	}
	*/
}
