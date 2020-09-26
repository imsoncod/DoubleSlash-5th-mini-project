package com.doubleslash.mini.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

@ComponentScan(basePackages = "com.doubleslash.mini.service")
@MapperScan(basePackages= "com.doubleslash.mini.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@Configuration
public class DataSourceConfig {
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource datasource() {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.build();
	}
	
	@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }
 
    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
