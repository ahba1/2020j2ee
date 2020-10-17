package com.ahba1.homework;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.ahba1.homework.mapper")
public class HomeworkApplication {
    public final static Logger logger = LoggerFactory.getLogger(HomeworkApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }
}
