package com.example.annotation.config.druid;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DataSourceConfig {

	private Logger log = Logger.getLogger(this.getClass());

	@Value("${db.initialSize:}")
	private String initialSize; // 初始化连接数
	@Value("${db.maxActive:}")
	private String maxActive; // 最大连接数
	@Value("${db.minIdle:}")
	private String minIdle; // 最小空间连接数
	@Value("${db.maxWait:}")
	private String maxWait; // 请求连接最大等待时间(ms)
	@Value("${db.validationQuery:}")
	private String validationQuery; // 验证连接有效的sql

	@Value("${db.driverClassName:}")
	private String driverClassName;
	@Value("${db.url:}")
	private String url;
	@Value("${db.password:}")
	private String password;
	@Value("${db.username:}")
	private String username;

	public DataSource getDruidDataSource() {
		DataSource dataSource = null;
		try {
			Properties properties = new Properties();

			// properties.setProperty("filters", "mergeStat");
			properties.setProperty("filters", "stat,slf4j"); // 统计sql
			properties.setProperty("connectionProperties", "druid.stat.slowSqlMillis=5000"); // 慢查询配置

			properties.setProperty("initialSize", initialSize);
			properties.setProperty("maxActive", maxActive);
			properties.setProperty("minIdle", minIdle);
			properties.setProperty("maxWait", maxWait);
			{ // 排查链接泄露，超时强行中断连接，打印日志 abandon connection, open stackTrace
				properties.setProperty("removeAbandoned", "true");
				properties.setProperty("logAbandoned", "true");
				properties.setProperty("removeAbandonedTimeout", "300"); // s
			}
			properties.setProperty("validationQuery", validationQuery);
			properties.setProperty("validationQueryTimeout", "10000"); // ms
			properties.setProperty("testWhileIdle", "true"); // 连接空闲时验证有效
			properties.setProperty("testOnBorrow", "true"); // 连接池借用时验证有效
			properties.setProperty("testOnReturn", "false"); // 连接归还时验证有效
			properties.setProperty("druid.notFullTimeoutRetryCount", "3"); // 重试次数
			properties.setProperty("driverClassName", driverClassName);
			properties.setProperty("url", url);
			properties.setProperty("username", username);
			properties.setProperty("password", password);
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			log.error("消息库数据源创建失败:" + url, e);
		}
		return dataSource;
	}

	@Bean
	public StatFilter getStatFilter() {
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(10000l);
		filter.setLogSlowSql(true);
		return filter;

	}

	@Bean
	public Log4jFilter getLog4jFilter() {
		Log4jFilter filter = new Log4jFilter();
		filter.setStatementExecutableSqlLogEnable(true);
		return filter;
	}
}