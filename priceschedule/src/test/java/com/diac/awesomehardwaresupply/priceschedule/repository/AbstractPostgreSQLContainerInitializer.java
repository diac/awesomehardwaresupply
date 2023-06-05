package com.diac.awesomehardwaresupply.priceschedule.repository;

import com.diac.awesomehardwaresupply.priceschedule.config.DataConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {
        DataConfig.class
})
public abstract class AbstractPostgreSQLContainerInitializer {

    private static final String SPRING_DATASOURCE_URL_PROPERTY = "spring.datasource.url";

    private static final String SPRING_DATASOURCE_USERNAME_PROPERTY = "spring.datasource.username";

    private static final String SPRING_DATASOURCE_PASSWORD_PROPERTY = "spring.datasource.password";

    @Container
    private static final PostgreSQLContainer<?> DB_CONTAINER = PriceSchedulePostgreSQLContainer.getInstance();

    @DynamicPropertySource
    static void registerPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add(SPRING_DATASOURCE_URL_PROPERTY, DB_CONTAINER::getJdbcUrl);
        registry.add(SPRING_DATASOURCE_USERNAME_PROPERTY, DB_CONTAINER::getUsername);
        registry.add(SPRING_DATASOURCE_PASSWORD_PROPERTY, DB_CONTAINER::getPassword);
    }
}