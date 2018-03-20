package com.franz.max2;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class Config {

	@Value("${spring.datasource.url}")
	private String dbUrl;


//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}

}
