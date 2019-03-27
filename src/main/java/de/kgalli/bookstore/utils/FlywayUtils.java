package de.kgalli.bookstore.utils;

import de.kgalli.common.ConnectionFactory;
import org.flywaydb.core.Flyway;

public class FlywayUtils {

    public static void migrate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(ConnectionFactory.getDataSource())
                .load();

        flyway.migrate();
    }
}
