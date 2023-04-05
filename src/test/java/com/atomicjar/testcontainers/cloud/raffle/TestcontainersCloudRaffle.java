package com.atomicjar.testcontainers.cloud.raffle;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static org.junit.Assert.assertTrue;

public class TestcontainersCloudRaffle {

    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine");

    @Test
    public void shouldRunWithTCC() throws Exception {
        String serverVersion = DockerClientFactory.instance().getInfo().getServerVersion();

        assertTrue("Must run with Testcontainers Cloud", serverVersion.endsWith("testcontainerscloud"));

        // Calculate SHA-256 with PostgreSQL... just because we can :D
        try (
                Connection connection = DriverManager.getConnection(
                        postgres.getJdbcUrl(),
                        postgres.getUsername(),
                        postgres.getPassword()
                );
                PreparedStatement statement = connection.prepareStatement("SELECT encode(sha256(?::bytea), 'hex')")
        ) {
            statement.setString(1, serverVersion);
            java.sql.ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String string = resultSet.getString(1);
            System.out.println("\nRaffle form link: https://bit.ly/" + string.substring(0, 8));
        }
    }
}
