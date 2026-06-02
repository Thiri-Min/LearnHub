package com.training.demo;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class DatabaseConfig {

    private final Environment env;

    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        String url = resolveUrl(properties);
        String driver = resolveDriver(properties, url);
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(properties.determineUsername())
                .password(properties.determinePassword())
                .build();
    }

    private String resolveUrl(DataSourceProperties properties) {
        String configuredUrl = properties.getUrl();
        if (StringUtils.hasText(configuredUrl)) {
            return configuredUrl;
        }

        String envUrl = env.getProperty("SPRING_DATASOURCE_URL");
        if (StringUtils.hasText(envUrl)) {
            return envUrl;
        }

        if (env.acceptsProfiles("prod")) {
            return "jdbc:h2:file:./data/demodb;DB_CLOSE_DELAY=-1";
        }

        return "jdbc:mysql://localhost:3306/demo_db?createDatabaseIfNotExist=true&serverTimezone=UTC";
    }

    private String resolveDriver(DataSourceProperties properties, String url) {
        String configuredDriver = properties.determineDriverClassName();
        if (StringUtils.hasText(configuredDriver)) {
            return configuredDriver;
        }

        String envDriver = env.getProperty("SPRING_DATASOURCE_DRIVER");
        if (StringUtils.hasText(envDriver)) {
            return envDriver;
        }

        if (url != null && url.startsWith("jdbc:postgresql")) {
            return "org.postgresql.Driver";
        }
        if (url != null && url.startsWith("jdbc:mysql")) {
            return "com.mysql.cj.jdbc.Driver";
        }
        return "org.h2.Driver";
    }
}
