package com.school.broadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.school.broadcast"})
public class SchoolVoiceBroadcastApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolVoiceBroadcastApplication.class, args);
    }

}
