package com.gn.sub.config;

import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@ImportResource({ "classpath:com/gn/config/conf/applicationContext_mybatis.xml"})
@EnableTransactionManagement
public class MybatisDatabaseBConfig {
	
	
	@Bean(name = "dataSourceB", initMethod = "init" , destroyMethod = "close")
	public AtomikosDataSourceBean dataSource() {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setUniqueResourceName("DataSourceB");
		atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		atomikosDataSourceBean.setPoolSize(10);
		Properties p = new Properties();
		p.setProperty("user","root");
		p.setProperty("password", "1234");
		p.setProperty("url", "jdbc:mysql://localhost:3306/mysql?useUnicode=true&amp;characterEncoding=utf-8");
		p.setProperty("pinGlobalTxToPhysicalConnection", "true");
		atomikosDataSourceBean.setXaProperties(p);
		return atomikosDataSourceBean;
	}
	@Bean(name = "sqlSessionFactoryB")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		
		Resource[] mapperLocations  = extracted().getResources("classpath:com/gn/web/dao/*.xml");
		
		for (Resource mapperLocation : mapperLocations) {
			
			String path;
			if (mapperLocation instanceof ClassPathResource) {
				path = ((ClassPathResource) mapperLocation).getPath();
			} else {
				path = mapperLocation.toString();
			}
			
			XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, path, configuration.getSqlFragments());
			xmlMapperBuilder.parse();
		}
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		sqlSessionFactoryBean.setSqlSessionFactoryBuilder(new SqlSessionFactoryBuilder());
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		return sqlSessionFactory;
		
	}
	private ClassPathXmlApplicationContext extracted() {
		return new ClassPathXmlApplicationContext();
	}
}