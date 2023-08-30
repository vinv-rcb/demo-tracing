package com.example.demotraceingv2x;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoTracingV2xApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTracingV2xApplication.class, args);
    }

}
