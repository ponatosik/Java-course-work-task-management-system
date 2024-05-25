package com.ponatosik.kanban.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.ponatosik.kanban.core.entities")
@ComponentScan(basePackages = {"com.ponatosik.kanban", "com.ponatosik.kanban.application.repositories"})
@SpringBootApplication
public class TaskManagementApp {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApp.class, args);
    }
}