package app.book;


import db.DBUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;

import static de.kgalli.javalin_example.tables.Books.*;

public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    public Iterable<Book> getAllBooks() {
        return findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return findByIsbn(isbn);
    }

    private Optional<Book> findByIsbn(String isbn) {
        try (var conn = DBUtils.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);

            return create.select().from(BOOKS)
                    .where(BOOKS.ISBN.eq(isbn))
                    .fetchOptionalInto(Book.class);
        }
        catch (DataAccessException| SQLException e) {
            logger.error("Could not find book by isbn", e);
            return Optional.empty();
        }
    }

    private List<Book> findAll() {
        try (var conn = DBUtils.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);

            return create.select().from(BOOKS).fetchInto(Book.class);
        }
        catch (DataAccessException|SQLException e) {
            logger.error("Could not find all books", e);
            return Collections.emptyList();
        }
    }
}
