package com.ggt.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing //타임스탬프..?
@EnableJpaRepositories
@SpringBootApplication
public class FinalprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalprojectApplication.class, args);
    }

}
