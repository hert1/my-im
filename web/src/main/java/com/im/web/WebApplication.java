package com.im.web;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubboConfiguration
@ComponentScan(basePackages = {"com.im.redis", "com.im.api","com.im.web","com.im.web.bean","com.im.web.config"})
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

          SpringApplication.run(WebApplication.class, args);
    }
    /**
     * war 包部署
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(WebApplication.class);
    }

}

