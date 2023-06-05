package com.diac.awesomehardwaresupply.priceschedule.repository;

import org.testcontainers.containers.PostgreSQLContainer;

public class PriceSchedulePostgreSQLContainer extends PostgreSQLContainer<PriceSchedulePostgreSQLContainer> {

    private static final String DB_DOCKER_IMAGE = "postgres:14.8";

    private static final String TEST_DB_NAME = "price-schedule-integration-tests-db";

    private static final String TEST_DB_USERNAME = "sa";

    private static final String TEST_DB_PASSWORD = "sa";

    private static PriceSchedulePostgreSQLContainer container;

    private PriceSchedulePostgreSQLContainer() {
        super(DB_DOCKER_IMAGE);
        this.withDatabaseName(TEST_DB_NAME);
        this.withUsername(TEST_DB_USERNAME);
        this.withPassword(TEST_DB_PASSWORD);
    }

    public static PriceSchedulePostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PriceSchedulePostgreSQLContainer();
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