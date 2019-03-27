package de.kgalli;

import com.google.inject.AbstractModule;
import de.kgalli.bookstore.book.BookModule;
import de.kgalli.bookstore.config.DbConfig;
import io.javalin.Javalin;

public class AppModule extends AbstractModule {
    private Javalin app;

    public AppModule(Javalin app) {
        this.app = app;
    }

    public static AppModule create() {
        return new AppModule(Javalin.create());
    }

    @Override
    protected void configure() {
        // TODO finalize the dbConfig /connection idea
        bind(DbConfig.class);
        install(new BookModule());
        bind(Javalin.class).toInstance(app);
    }
}
