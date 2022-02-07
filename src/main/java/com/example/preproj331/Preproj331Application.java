package com.example.preproj331;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.preproj331.repo")
@EntityScan("com.example.preproj331.model")
@SpringBootApplication
public class Preproj331Application {

    public static void main(String[] args) {
        SpringApplication.run(Preproj331Application.class, args);
    }

}
