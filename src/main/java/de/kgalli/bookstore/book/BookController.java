package de.kgalli.bookstore.book;

import io.javalin.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class BookController {

    private BookService bookService;

    @Inject
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void fetchAllBooks(Context ctx) {
        ctx.status(200).json(bookService.getAllBooks());
    }

    public void fetchOneBook(Context ctx) {
        var bookId = ctx.pathParam("isbn");
        var book = bookService.getBookByIsbn(bookId);

        book.ifPresentOrElse(
                value -> { ctx.status(200).json(value); },
                ()-> { ctx.status(404).json(new HashMap<>()); });

    }
}
