package com.diac.awesomehardwaresupply.priceschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PriceScheduleApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PriceScheduleApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PriceScheduleApp.class, args);
    }
}