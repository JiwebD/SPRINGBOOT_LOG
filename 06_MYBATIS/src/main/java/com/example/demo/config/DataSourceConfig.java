package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	// Spring-jdbc DataSource
	@Bean
	public DataSource dataSource2() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/testdb");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		dataSource.setInitialSize(5); // 초기 연결개수
		dataSource.setMaxTotal(10); // 최대 연결 개수
		dataSource.setMaxIdle(8); // 최대 유휴(쉬어도되는) 연결 수
		dataSource.setMinIdle(3); // 최소 유휴(쉬어도되는) 연결 수

		return dataSource;
	}

//	HikariCP DataSource
	@Bean
	public HikariDataSource dataSource3() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}

}
