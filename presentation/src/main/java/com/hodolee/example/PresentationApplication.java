package com.hodolee.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.hodolee.example")
public class PresentationApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-infrastructure");
        SpringApplication.run(PresentationApplication.class, args);
    }

}
