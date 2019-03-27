package de.kgalli.bookstore.book;

import de.kgalli.common.Routing;
import de.kgalli.bookstore.utils.Path;
import io.javalin.Javalin;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

@Singleton
public class BookRouting extends Routing<BookController> {

    private Javalin javalin;

    @Inject
    public BookRouting(Javalin javalin) {
        this.javalin = javalin;
    }

    @Override
    public void bindRoutes() {
        javalin.routes(() -> {
            path(Path.Api.BOOKS, () ->
                    get(ctx -> getController().fetchAllBooks(ctx)));

            path(Path.Api.ONE_BOOK, () ->
                get(ctx -> getController().fetchOneBook(ctx)));
        });
    }
}
