package com.diac.awesomehardwaresupply.authentication.repository.container;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Value;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLTestContainersExtension implements BeforeAllCallback, AfterAllCallback {

    private static final String SPRING_DATASOURCE_URL_PROPERTY = "spring.datasource.url";

    private static final String SPRING_DATASOURCE_USERNAME_PROPERTY = "spring.datasource.username";

    private static final String SPRING_DATASOURCE_PASSWORD_PROPERTY = "spring.datasource.password";

    @Value("${enable-test-containers}")
    private boolean enableTestContainers = true;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!enableTestContainers) {
            return;
        }
        PostgreSQLContainer<?> dbContainer = AuthenticationPostgreSQLContainer.getInstance();
        dbContainer.start();
        System.setProperty(SPRING_DATASOURCE_URL_PROPERTY, dbContainer.getJdbcUrl());
        System.setProperty(SPRING_DATASOURCE_USERNAME_PROPERTY, dbContainer.getUsername());
        System.setProperty(SPRING_DATASOURCE_PASSWORD_PROPERTY, dbContainer.getPassword());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {

    }
}