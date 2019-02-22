package db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static config.ApplicationProperties.*;

public class DBUtils {

    private static Logger logger = LoggerFactory.getLogger(DBUtils.class);

    public static final Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getDBUrl(), getDBUser(), getDBPassword());
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
