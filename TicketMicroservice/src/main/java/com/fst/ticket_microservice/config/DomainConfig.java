package com.fst.ticket_microservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.fst.ticket_microservice.domain")
@EnableJpaRepositories("com.fst.ticket_microservice.repos")
@EnableTransactionManagement
public class DomainConfig {
}
