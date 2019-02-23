import app.App;
import app.books.BooksRoutes;
import config.AppConfig;
import db.DBUtils;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        var appConfig = new AppConfig();
        var app = new App(new AppConfig(), Arrays.asList(BooksRoutes.add()));

        DBUtils.migrate();
        app.run();
    }
}
