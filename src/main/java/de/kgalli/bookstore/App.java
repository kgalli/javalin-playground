package de.kgalli.bookstore;

import com.google.inject.Inject;
import de.kgalli.bookstore.config.AppConfig;
import de.kgalli.common.Routing;
import io.javalin.Javalin;
import io.javalin.RequestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Singleton;
import java.util.Collections;
import java.util.Set;

import static de.kgalli.bookstore.config.AppConfig.EnvironmentType.DEFAULT;
import static de.kgalli.bookstore.config.AppConfig.EnvironmentType.TEST;

@Singleton
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private Javalin app;
    private AppConfig appConfig;

    @Inject(optional = true)
    private Set<Routing> routes = Collections.emptySet();

    @Inject
    public App(Javalin app, AppConfig appConfig) {
        this.app = app;
        this.appConfig = appConfig;
    }

    public void boot(String[] args) {
        var env = getEnvironmentTypeByBootArgs(args);

        bindRoutes();
        app.port(appConfig.getAppPort(env));
        app.enableRouteOverview("/routes");
        app.requestLogger(getCustomRequestLogger());
        app.start();
    }

    public void stop() {
        if (app == null) {
            throw new IllegalStateException("App has not been called before initialization");
        }

        app.stop();
    }

    private void bindRoutes() {
        routes.forEach(Routing::bindRoutes);
    }

    private RequestLogger getCustomRequestLogger() {
        return (ctx, timeMs) -> {
            logger.info("{} {} -- {} -- {} ms", ctx.method(), ctx.path(), ctx.status(), Math.round(timeMs));
        };
    }

    private AppConfig.EnvironmentType getEnvironmentTypeByBootArgs(String[] args) {
        var envString = args.length > 0 ? args[0] : "default";

        return envString.equals("test") ? TEST : DEFAULT;
    }
}
