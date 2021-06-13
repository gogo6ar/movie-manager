package com.example.myspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Component;

@EnableScheduling
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringProjectApplication.class, args);
    }

}
