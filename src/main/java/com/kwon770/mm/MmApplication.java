package com.kwon770.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmApplication.class, args);
    }

}
