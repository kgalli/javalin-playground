import de.kgalli.AppModule;
import de.kgalli.bookstore.App;
import com.google.inject.Guice;
import de.kgalli.common.ConnectionFactory;
import de.kgalli.bookstore.utils.FlywayUtils;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        ConnectionFactory.connect();
        FlywayUtils.migrate();

        var injector = Guice.createInjector(AppModule.create());
        injector.getInstance(App.class).boot(args);
    }
}
