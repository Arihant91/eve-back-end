package com.backend.eve.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

@Configuration
public class CassandraConfig {

    @Value("${spring.data.cassandra.url}")
    private String url;
    @Value("${spring.data.cassandra.port}")
    private Integer port;
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;
    @Value("${spring.data.cassandra.username}")
    private String username;
    @Value("${spring.data.cassandra.password}")
    private String pass;
    @Value("${spring.data.cassandra.data-center}")
    private String dataCenter;

    @Bean
    public CqlSessionFactoryBean cqlSessionFactoryBean() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(url);
        session.setKeyspaceName(keySpace);
        session.setUsername(username);
        session.setPassword(pass);
        session.setLocalDatacenter(dataCenter);
        return session;
    }
}