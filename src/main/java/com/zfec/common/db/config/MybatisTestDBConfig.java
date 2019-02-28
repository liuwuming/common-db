package com.zfec.common.db.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//@ComponentScan
@Configuration
@EnableTransactionManagement
@AutoConfigureAfter({TestDBConfig.class})
@MapperScan(basePackages = {"com.zfec.demoservicee.dao"})
public class MybatisTestDBConfig implements EnvironmentAware {
	@Autowired
	DataSource testdbSource;

	private String typeAliasesPackage = null;

	public void setEnvironment(Environment environment) {
		typeAliasesPackage = environment.getProperty("mybatis.typeAliasesPackage");
	}

	@Bean(name = "testdbSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		try {
			ResourcePatternResolver resourcePatternResolver;
			resourcePatternResolver = new PathMatchingResourcePatternResolver();

			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			bean.setDataSource(testdbSource);
			bean.setTypeAliasesPackage(typeAliasesPackage);
			// bean.setMapperLocations(resourcePatternResolver.getResources(propertyResolver.getProperty("mapper-locations")));
			// bean.setConfigLocation(new
			// DefaultResourceLoader().getResource(propertyResolver.getProperty("configLocation")));

			return bean.getObject();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean(name = "testdbPlatformTransactionManager")
	public PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(testdbSource);
	}
}
