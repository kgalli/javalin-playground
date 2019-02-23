package app.books;

import io.javalin.Handler;

import java.util.HashMap;

import static app.Main.*;

public class BooksController {

    public static Handler fetchAllBooks = ctx -> {
        ctx.status(200).json(booksService.getAllBooks());
    };

    public static Handler fetchOneBook = ctx -> {
        var bookId = ctx.pathParam("isbn");
        var book = booksService.getBookByIsbn(bookId);

        book.ifPresentOrElse(
                value -> { ctx.status(200).json(value); },
                ()-> { ctx.status(404).json(new HashMap<>()); });

    };
}
