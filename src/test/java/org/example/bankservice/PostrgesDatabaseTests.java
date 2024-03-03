package org.example.bankservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public interface PostrgesDatabaseTests {

    String POSTGRES_USERNAME = "root";
    String POSTGRES_PASSWORD = "root";
    String POSTGRES_DATABASE= "test";

    @Container
    PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName(POSTGRES_DATABASE)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD)
            .withInitScript(new ClassPathResource("init.sql").getPath());

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);

        POSTGRES.withReuse(true).start();
    }

}
