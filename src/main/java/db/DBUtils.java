package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.DbConfig;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.*;


public class DBUtils {

    private static HikariDataSource dataSource = null;
    private static DbConfig dbConfig = null;

    public static final Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void migrate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(getDataSource())
                .load();

        flyway.migrate();
    }

    public static void setDbConfig(DbConfig config) {
        dbConfig = config;
    }

    private static DbConfig getDbConfig() {
        if (dbConfig ==  null) {
            dbConfig = new DbConfig();
        }

        return dbConfig;
    }

    private static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(getDbConfig().getUrl());
            hikariConfig.setUsername(getDbConfig().getUser());
            hikariConfig.setPassword(getDbConfig().getPassword());
            dataSource = new HikariDataSource(hikariConfig);
        }

        return dataSource;
    }
}
