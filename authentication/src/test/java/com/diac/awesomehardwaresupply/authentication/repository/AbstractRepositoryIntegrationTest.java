package com.diac.awesomehardwaresupply.authentication.repository;

import com.diac.awesomehardwaresupply.authentication.config.DataConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class
})
public abstract class AbstractRepositoryIntegrationTest {

}