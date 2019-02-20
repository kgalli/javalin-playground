package app.book;

import db.JDBCHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BookService {

    private final List<Book> books;

    public BookService() {
        books = bookStore();
    }

    public Iterable<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return db.JDBCHelper.getBook(isbn);

        /*
        return books.stream()
                .filter(b -> b.isbn.equals(isbn))
                .findFirst();
                */
    }

    public Book getRandomBook() {
        return books.get(new Random().nextInt(books.size()));
    }

    private List<Book> bookStore()  {
        var books = new ArrayList<Book>();

        books.add(new Book("Moby Dick", "Herman Melville", "9789583001215"));
        books.add(new Book("A Christmas Carol", "Charles Dickens", "9780141324524"));
        books.add(new Book("Pride and Prejudice", "Jane Austen", "9781936594290"));
        books.add(new Book("The Fellowship of The Ring", "J. R. R. Tolkien", "0007171978"));
        books.add(new Book("Harry Potter", "J. K. Rowling", "0747532699"));
        books.add(new Book("War and Peace", "Leo Tolstoy", "9780060798871"));
        books.add(new Book("Don Quixote", "Miguel Cervantes", "9789626345221"));
        books.add(new Book("Ulysses", "James Joyce", "9780394703800"));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        books.add(new Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "9780060531041"));
        books.add(new Book("The adventures of Huckleberry Finn", "Mark Twain", "9781591940296"));
        books.add(new Book("Alice In Wonderland", "Lewis Carrol", "9780439291491"));

        return books;
    }
}
