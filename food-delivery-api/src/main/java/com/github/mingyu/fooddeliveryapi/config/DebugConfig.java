package com.github.mingyu.fooddeliveryapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${secret.key}")
    private String jwtSecret;

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            System.out.println("DB_URL: " + dbUrl);
            System.out.println("DB_USERNAME: " + dbUsername);
            System.out.println("DB_PASSWORD: " + dbPassword);
            System.out.println("KAFKA_BOOTSTRAP_SERVERS: " + kafkaBootstrapServers);
            System.out.println("JWT_SECRET: " + jwtSecret);
        };
    }
}