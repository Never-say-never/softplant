package com.softwareplant.api.container.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.softwareplant.api.module.**")
@EntityScan("com.softwareplant.api.module.**")
@EnableJpaAuditing
public class PersistenceConfiguration {}
