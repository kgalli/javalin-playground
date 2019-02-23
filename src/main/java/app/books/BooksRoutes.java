package app.books;

import app.utils.Path;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;

public class BooksRoutes {

    public static EndpointGroup add() {
        var booksController = getControllerWithInitializedDependencies();

        return () -> {
            get(Path.Api.BOOKS, booksController.fetchAllBooks);
            get(Path.Api.ONE_BOOK, booksController.fetchOneBook);
        };
    }

    private static BooksController getControllerWithInitializedDependencies() {
        var bookRepository = new BooksRepository();
        var booksService = new BooksService(bookRepository);

        return new BooksController(booksService);
    }
}
