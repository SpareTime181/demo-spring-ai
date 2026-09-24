package com.sparetime.demospringai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sparetime.demospringai.mapper")
public class DemoSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringAiApplication.class, args);
    }

}
