package app.book;

import io.javalin.Handler;

import java.util.HashMap;

import static app.Main.*;

public class BookController {

    public static Handler fetchAllBooks = ctx -> {
        ctx.status(200).json(bookService.getAllBooks());
    };

    public static Handler fetchOneBook = ctx -> {
        var bookId = ctx.pathParam("isbn");
        var book = bookService.getBookByIsbn(bookId);

        book.ifPresentOrElse(
                value -> { ctx.status(200).json(value); },
                ()-> { ctx.status(404).json(new HashMap<>()); });

    };
}
