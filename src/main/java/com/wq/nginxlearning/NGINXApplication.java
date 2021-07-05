package com.wq.nginxlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NGINXApplication{
    public static void main(String[] args) {
        // SpringApplication.run(NGINXApplication.class);
        SpringApplication springApplication = new SpringApplication(NGINXApplication.class);
        // 监听生命周期
        // springApplication.addListeners(new ());
        // 问题一、这里传入命令行配置的参数，如--server.port=xx才能生效
        // 问题二、同时要记得 在Linux防火墙中开放该端口！！！
        springApplication.run(args);
    }
}
