package app.books;

import io.javalin.Handler;

import java.util.HashMap;

public class BooksController {

    private BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    public Handler fetchAllBooks = ctx -> {
        ctx.status(200).json(booksService.getAllBooks());
    };

    public Handler fetchOneBook = ctx -> {
        var bookId = ctx.pathParam("isbn");
        var book = booksService.getBookByIsbn(bookId);

        book.ifPresentOrElse(
                value -> { ctx.status(200).json(value); },
                ()-> { ctx.status(404).json(new HashMap<>()); });

    };
}
