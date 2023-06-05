package com.diac.awesomehardwaresupply.authentication.repository.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class AuthenticationPostgreSQLContainer extends PostgreSQLContainer<AuthenticationPostgreSQLContainer> {

    private static final String DB_DOCKER_IMAGE = "postgres:14.8";

    private static final String TEST_DB_NAME = "authentication-integration-tests-db";

    private static final String TEST_DB_USERNAME = "sa";

    private static final String TEST_DB_PASSWORD = "sa";

    private static AuthenticationPostgreSQLContainer container;

    private AuthenticationPostgreSQLContainer() {
        super(DB_DOCKER_IMAGE);
        this.withDatabaseName(TEST_DB_NAME);
        this.withUsername(TEST_DB_USERNAME);
        this.withPassword(TEST_DB_PASSWORD);
    }

    public static AuthenticationPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new AuthenticationPostgreSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {

    }
}