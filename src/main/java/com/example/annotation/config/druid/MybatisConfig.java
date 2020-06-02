package com.example.annotation.config.druid;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan("com.example.annotation.mapper")
public class MybatisConfig {
	// 业务库数据源
	@Autowired
	private DataSource dataSource;
	// mapper扫描路径
	private final static String mapperPath = "classpath*:com/example/annotation/sqlmap/**/sqlmap-*.xml"; 
	// 包下所有Bean使用 Bean的首字母小写的非限定类名来作为它的别名,如:
	// User 的别名为 user。注解优先
	private final static String typeAliasesPackage = "com.example.annotation.model.po"; 
	
	// 全局的映射器启用或禁用 缓存
	private final static boolean cacheEnabled = true; 
	// 全局启用或禁用延迟加载
	private final static boolean lazyLoadingEnabled = true; 
	// 启用时,有延迟加载属性的对象在被调用时将会完全加载。否则按需要加载
	private final static boolean aggressiveLazyLoading = false; 
	// 允许或不允许多种结果集达到通用的效果
	private final static boolean multipleResultSetsEnabled = true; 
	// 允许使用列标签代替列名
	private final static boolean useColumnLabel = true; 
	// 允许使用自定义的主键值(比如由程序生成的UUID 32位编码作为键值)，数据表的PK生成策略将被覆盖
	private final static boolean useGeneratedKeys = true; 
	// 属性的映射支持 , NONE不自动映射,
	// PARTIAL简单映射, 没有嵌套的结果;
	// FULL 会自动映射任意复杂的结果(嵌套的或其他情况)
	private final static AutoMappingBehavior autoMappingBehavior = AutoMappingBehavior.FULL; 
	// SIMPLE 默认执行器;REUSE 重用 预处理语句;BATCH
	// 重用语句 和批量更新
	private final static ExecutorType defaultExecutorType = ExecutorType.SIMPLE; 
	// 超时时间，驱动等待数据库响应的秒数
	private final static Integer defaultStatementTimeout = 120; 
	// 控制获取返回结果的大小,这个参数值可以覆盖一个查询设置
	private final static Integer defaultFetchSize = 100000; 
	// 允许在嵌套语句中使用分页
	private final static boolean safeRowBoundsEnabled = true; 
	// 开启自动驼峰命名规则映射，即从数据库列名 A_COLUMN到Java属性名aColumn的类似映射
	private final static boolean mapUnderscoreToCamelCase = true; 
	// 默认值为 SESSION，缓存一个会话中执行的所有查询。 若为
	// STATEMENT，仅用在语句执行上，对相同
	// SqlSession的不同调用将不会共享数据
	private final static LocalCacheScope localCacheScope = LocalCacheScope.SESSION; 
	// 为空值指定 JDBC 类型 如:VARCHAR
	private final static JdbcType jdbcTypeForNull = JdbcType.NULL; 
	// 设置触发延迟加载的函数
	private final static Set<String> lazyLoadTriggerMethods;

	static {
		lazyLoadTriggerMethods = new HashSet<String>();
		lazyLoadTriggerMethods.add("equals");
		lazyLoadTriggerMethods.add("clone");
		lazyLoadTriggerMethods.add("hashCode");
	}

	/**
	 * mybatis配置入口
	 * 2020年6月2日
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws NamingException
	 */
	@Bean
	@Primary
	public SqlSessionFactoryBean sqlSessionFactory() throws IOException, IllegalArgumentException, NamingException {

		// 注入数据源
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);

		// 加载全局配置
		setConfig(sqlSessionFactoryBean);

		// 设置自动别名解析路径
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

		// sql-mapper 文件扫描位置
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] mapperLocations = resolver.getResources(mapperPath);
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		// 配置插件
		//sqlSessionFactoryBean.setPlugins(plugins);

		return sqlSessionFactoryBean;
	}

	/**
	 * 事物配置
	 * 2020年6月2日
	 * @return
	 */
	@Bean
	@Primary
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * Mybatis基本配置
	 * 2020年6月2日
	 * @param sqlSessionFactoryBean
	 */
	private void setConfig(SqlSessionFactoryBean sqlSessionFactoryBean) {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(cacheEnabled);
		configuration.setLazyLoadingEnabled(lazyLoadingEnabled);
		configuration.setAggressiveLazyLoading(aggressiveLazyLoading);
		configuration.setMultipleResultSetsEnabled(multipleResultSetsEnabled);
		configuration.setUseColumnLabel(useColumnLabel);
		configuration.setUseGeneratedKeys(useGeneratedKeys);
		configuration.setAutoMappingBehavior(autoMappingBehavior);
		configuration.setDefaultExecutorType(defaultExecutorType);
		configuration.setDefaultStatementTimeout(defaultStatementTimeout);
		configuration.setDefaultFetchSize(defaultFetchSize);
		configuration.setSafeRowBoundsEnabled(safeRowBoundsEnabled);
		configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
		configuration.setLocalCacheScope(localCacheScope);
		configuration.setJdbcTypeForNull(jdbcTypeForNull);
		configuration.setLazyLoadTriggerMethods(lazyLoadTriggerMethods);
		sqlSessionFactoryBean.setConfiguration(configuration);
	}
}