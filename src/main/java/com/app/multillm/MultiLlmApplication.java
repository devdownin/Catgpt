package com.app.multillm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MultiLlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiLlmApplication.class, args);
    }

}
