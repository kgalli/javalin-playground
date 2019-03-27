package de.kgalli.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.kgalli.bookstore.config.DbConfig;

import javax.sql.DataSource;
import java.sql.*;


public class ConnectionFactory {

    private static HikariDataSource dataSource = null;
    private static DbConfig dbConfig = null;

    public static void connect() {
        try {
            getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(getDbConfig().getUrl());
            hikariConfig.setUsername(getDbConfig().getUser());
            hikariConfig.setPassword(getDbConfig().getPassword());
            dataSource = new HikariDataSource(hikariConfig);
        }

        return dataSource;
    }

    public static final Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
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
}
