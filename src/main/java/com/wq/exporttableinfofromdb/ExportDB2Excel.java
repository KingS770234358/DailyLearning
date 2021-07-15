package com.wq.exporttableinfofromdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExportDB2Excel {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ExportDB2Excel.class);
        springApplication.run(args);
    }
}
