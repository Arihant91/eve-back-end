package com.backend.eve.client.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfig {
    @Bean
    public CqlSession session() {
        return CqlSession.builder().withKeyspace("orders").build();
    }
}