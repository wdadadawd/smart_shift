package com.lsu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lsu.mapper")
@SpringBootApplication
public class SmartShiftApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartShiftApplication.class, args);
    }

}
