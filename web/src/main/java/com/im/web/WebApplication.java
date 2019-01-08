package com.im.web;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDubboConfiguration
@ComponentScan(basePackages = {"com.im.redis", "com.im.api","com.im.web","com.im.web.bean"})
public class WebApplication {

    public static void main(String[] args) {

          SpringApplication.run(WebApplication.class, args);
    }

}

