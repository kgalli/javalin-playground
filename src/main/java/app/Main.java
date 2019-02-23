package app;

import app.books.BooksController;
import app.books.BooksRepository;
import app.books.BooksService;
import app.utils.Path;
import db.DBUtils;
import io.javalin.Javalin;
import io.javalin.RequestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static io.javalin.apibuilder.ApiBuilder.*;
import static config.ApplicationProperties.*;

public class Main {

    public static Logger logger;
    public static BooksService booksService;

    public static void main(String[] args) {

        initializeDependencies();
        setSystemPropertiesViaConfigFile();
        DBUtils.migrate();

        var app = startApp();

        app.routes(() -> {
            get(Path.Api.BOOKS, BooksController.fetchAllBooks);
            get(Path.Api.ONE_BOOK, BooksController.fetchOneBook);
        });

        app.error(404, ctx -> { ctx.json(new HashMap<>()); });
    }

    private static void initializeDependencies() {

        // books feature
        var bookRepository = new BooksRepository();
        booksService = new BooksService(bookRepository);

        logger = LoggerFactory.getLogger(Main.class);
    }

    private static Javalin startApp() {
        return Javalin.create()
                .port(3000)
                .enableRouteOverview("/routes")
                .requestLogger(getCustomRequestLogger())
                .start();
    }

    private static RequestLogger getCustomRequestLogger() {
        return (ctx, timeMs) -> {
            logger.info("{} {} -- {} -- {} ms", ctx.method(), ctx.path(), ctx.status(), Math.round(timeMs));
        };
    }

    private static void setSystemPropertiesViaConfigFile() {
        var dbUrl = "jdbc:postgresql://localhost:9999/kgalli?user=kgalli&password=kgalli";
        var username = "kgalli";
        var password = "password";

        // TODO read actual application.properties file
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        System.getProperties().setProperty(DB_URL.displayName(), dbUrl);
        System.getProperties().setProperty(DB_USERNAME.displayName(), username);
        System.getProperties().setProperty(DB_PASSWORD.displayName(), password);
    }
}
