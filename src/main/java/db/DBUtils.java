package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.*;

import static config.ApplicationProperties.*;

public class DBUtils {

    private static HikariDataSource dataSource = null;

    public static final Connection getConnection() throws SQLException {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(getDBUrl());
            config.setUsername(getDBUser());
            config.setPassword(getDBPassword());
            dataSource = new HikariDataSource(config);

            return dataSource.getConnection();
        }

        return dataSource.getConnection();
    }

    public static void migrate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(getDBUrl(), getDBUser(), getDBPassword())
                .load();

        flyway.migrate();
    }

    private static String getDBUrl() {
        return System.getProperties().getProperty(DB_URL.displayName());
    }

    private static String getDBUser() {
        return System.getProperties().getProperty(DB_USERNAME.displayName());
    }

    private static String getDBPassword() {
        return System.getProperties().getProperty(DB_PASSWORD.displayName());
    }
}
