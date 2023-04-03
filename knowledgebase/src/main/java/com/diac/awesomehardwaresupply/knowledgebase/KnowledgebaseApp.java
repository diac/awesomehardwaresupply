package com.diac.awesomehardwaresupply.knowledgebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KnowledgebaseApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KnowledgebaseApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(KnowledgebaseApp.class, args);
    }
}