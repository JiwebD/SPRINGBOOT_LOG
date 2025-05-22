package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

//	HikariCP DataSource
	@Bean
	public HikariDataSource dataSource()
	{
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");	
		 
		return dataSource;
	}
	
	
}
