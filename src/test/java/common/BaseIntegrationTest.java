package common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.kgalli.AppModule;
import de.kgalli.bookstore.App;
import de.kgalli.bookstore.config.DbConfig;
import de.kgalli.bookstore.utils.FlywayUtils;
import de.kgalli.common.ConnectionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseIntegrationTest {
    private static Injector injector;
    public Integer APP_PORT = 3099;

    @BeforeAll
    protected static void startApp() {
        System.setProperty("org.jooq.no-logo", "true");

        var dbConfig = new DbConfig();
        dbConfig.setPassword("password");
        dbConfig.setUser("sa");
        // dataSource.setUrl("jdbc:h2:mem:test;MODE=Mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=CREATE SCHEMA IF NOT EXISTS MYAPP");
        dbConfig.setDbUrl("jdbc:h2:mem:test_mem;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;CASE_INSENSITIVE_IDENTIFIERS=true");

        ConnectionFactory.setDbConfig(dbConfig);
        FlywayUtils.migrate();

        var args = new String[1];
        args[0] = "test";

        injector = Guice.createInjector(AppModule.create());
        injector.getInstance(App.class).boot(args);
    }

    @AfterAll
    protected static void stopApp() {
        if (injector == null) {
            throw new IllegalStateException("App has not been started yet");
        }
        injector.getInstance(App.class).stop();
    }
}
