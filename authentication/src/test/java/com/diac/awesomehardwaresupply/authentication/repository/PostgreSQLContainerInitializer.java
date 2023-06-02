package com.diac.awesomehardwaresupply.authentication.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface PostgreSQLContainerInitializer {

    String DB_DOCKER_IMAGE = "postgres:14.8";

    String TEST_DB_NAME = "knowledgebase-integration-tests-db";

    String TEST_DB_USERNAME = "sa";

    String TEST_DB_PASSWORD = "sa";

    String SPRING_DATASOURCE_URL_PROPERTY = "spring.datasource.url";

    String SPRING_DATASOURCE_USERNAME_PROPERTY = "spring.datasource.username";

    String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";

    @Container
    PostgreSQLContainer<?> DB_CONTAINER
            = new PostgreSQLContainer<>(DB_DOCKER_IMAGE)
            .withDatabaseName(TEST_DB_NAME)
            .withUsername(TEST_DB_USERNAME)
            .withPassword(TEST_DB_PASSWORD);

    @DynamicPropertySource
    static void registerPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add(SPRING_DATASOURCE_URL_PROPERTY, DB_CONTAINER::getJdbcUrl);
        registry.add(SPRING_DATASOURCE_USERNAME_PROPERTY, DB_CONTAINER::getUsername);
        registry.add(SPRING_DATASOURCE_PASSWORD, DB_CONTAINER::getPassword);
    }

    @BeforeAll
    static void start() {
        DB_CONTAINER.start();
    }

    @AfterAll
    static void stop() {
        DB_CONTAINER.stop();
    }
}