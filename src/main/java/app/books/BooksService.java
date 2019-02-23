package app.books;


import java.util.*;

public class BooksService {

    private BooksRepository repository;

    public BooksService(BooksRepository repository) {
        this.repository = repository;
    }

    public Iterable<Book> getAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }
}
