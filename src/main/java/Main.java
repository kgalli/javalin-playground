import app.App;
import app.books.BooksRoutes;
import db.DBUtils;

import java.util.Arrays;

import static config.ApplicationProperties.*;

public class Main {

    public static void main(String[] args) {

        setSystemPropertiesViaConfigFile();
        DBUtils.migrate();

        var app = new App(3000, Arrays.asList(BooksRoutes.booksRoutes()));
        app.run();
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
