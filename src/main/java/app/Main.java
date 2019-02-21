package app;

import app.book.BookController;
import app.book.BookService;
import app.util.Path;
import io.javalin.Javalin;
import io.javalin.RequestLogger;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static Logger logger;
    public static BookService bookService;

    public static void main(String[] args) {

        bookService = new BookService();
        logger = LoggerFactory.getLogger(Main.class);

        migrateDb();

        Javalin app = Javalin.create()
                .port(3000)
                .enableRouteOverview("/routes")
                .requestLogger(getCustomRequestLogger())
                .start();

        app.routes(() -> {
            get(Path.Api.BOOKS, BookController.fetchAllBooks);
            get(Path.Api.ONE_BOOK, BookController.fetchOneBook);
        });

        app.error(404, ctx -> { ctx.json(new HashMap<>()); });
    }

    private static RequestLogger getCustomRequestLogger() {
        return (ctx, timeMs) -> {
            logger.info("{} {} -- {} -- {} ms", ctx.method(), ctx.path(), ctx.status(), Math.round(timeMs));
        };
    }

    private static void migrateDb() {
        var dbUrl = "jdbc:postgresql://localhost:9999/kgalli?user=kgalli&password=kgalli";
        var username = "kgalli";
        var password = "password";

        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway.configure().dataSource(dbUrl, username, password).load();

        // Start the migration
        flyway.migrate();
    }
}
