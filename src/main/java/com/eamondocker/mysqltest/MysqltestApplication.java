package com.eamondocker.mysqltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eamondocker.mysqltest.mapper")
public class MysqltestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysqltestApplication.class, args);
    }

}
