package com.ponatosik.kanban.presentation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "com.ponatosik.kanban.application.repositories")
@EnableAutoConfiguration
public class AppConfiguration {
}
