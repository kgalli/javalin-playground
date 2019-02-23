package app;

import config.AppConfig;
import io.javalin.ExceptionHandler;
import io.javalin.Javalin;
import io.javalin.RequestLogger;
import io.javalin.apibuilder.EndpointGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);
    private Javalin app;
    private AppConfig config;

    public App(AppConfig config, List<EndpointGroup> routes) {
        this.config = config;
        this.app = Javalin.create()
                .port(config.getAppPort())
                .enableRouteOverview("/routes")
                .requestLogger(getCustomRequestLogger());

        app.routes(addEndpoints(routes));
        app.exception(Exception.class, handleInternalServerError());
        app.error(404, ctx -> ctx.json(new HashMap<>()));
    }

    public AppConfig getAppConfig() {
        return this.config;
    }

    public void run() {
        if (app == null) {
            throw new IllegalStateException("App has not been called before initialization");
        }

        app.start();
    }

    public void stop() {
        if (app == null) {
            throw new IllegalStateException("App has not been called before initialization");
        }

        app.stop();
    }

    private EndpointGroup addEndpoints(List<EndpointGroup> routes) {
        return () -> routes.forEach((route) -> route.addEndpoints());
    }

    private ExceptionHandler handleInternalServerError() {
        return (e, ctx) -> {
            var problemResponse = new HashMap<>();

            problemResponse.put("details", e.getMessage());
            logger.error(e.getMessage(), e);

            ctx.status(500).json(problemResponse);
        };
    }

    private RequestLogger getCustomRequestLogger() {
        return (ctx, timeMs) -> {
            logger.info("{} {} -- {} -- {} ms", ctx.method(), ctx.path(), ctx.status(), Math.round(timeMs));
        };
    }
}
