package com.yeshenko.director;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DirectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DirectorApplication.class, args);
    }
}