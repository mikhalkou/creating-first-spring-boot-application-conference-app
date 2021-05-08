package com.mikhalkou.learn.springboot1.conferencespringboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    Logger logger = LoggerFactory.getLogger(PersistenceConfiguration.class);
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.username}")
    private String username;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        String dbUrl = System.getenv("DB_URL");
        logger.info("Initializing persistence with {}", dbUrl);
        builder.url(dbUrl);
        builder.password(password);
        builder.username(username);
        return builder.build();
    }
}
