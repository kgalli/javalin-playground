package de.kgalli.bookstore.book;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class BookService {

    private BookRepository repository;

    @Inject
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Iterable<Book> getAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }
}
