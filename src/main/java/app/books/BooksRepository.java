package app.books;

import app.exceptions.RepositoryException;
import com.fasterxml.jackson.core.type.TypeReference;
import db.DBUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static de.kgalli.javalin_example.Tables.BOOKS;

public class BooksRepository {

    public Book create(Book book) {
        try (var conn = DBUtils.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.H2);

            var newBook = create.newRecord(BOOKS);

            newBook.setAuthor(book.getAuthor());
            newBook.setIsbn(book.getIsbn());
            newBook.setTitle(book.getTitle());
            newBook.store();

            return findByIsbn(book.getIsbn())
                    .orElseThrow(() -> new RepositoryException("Could not find created book"));
        }
        catch (DataAccessException | SQLException e) {
            throw new RepositoryException("Could not create book", e);
        }
    }

    public Optional<Book> findByIsbn(String isbn) {
        try (var conn = DBUtils.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.H2);

            return create.select().from(BOOKS)
                    .where(BOOKS.ISBN.eq(isbn))
                    .fetchOptionalInto(Book.class);
        }
        catch (DataAccessException | SQLException e) {
            throw new RepositoryException("Could not find books by isbn", e);
        }
    }

    public List<Book> findAll() {
        try (var conn = DBUtils.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.H2, new Settings().withRenderNameStyle(RenderNameStyle.AS_IS));

            return create.select().from(BOOKS).fetchInto(Book.class);
        }
        catch (DataAccessException|SQLException e) {
            throw new RepositoryException("Could not find all books", e);
        }
    }

/*
    public List<Book> findAll() {
        Function<DSLContext, List<Book>> books = (ctx) ->
                ctx.select().from(BOOKS).fetchInto(Book.class);

        return findMany(books);
    }

    public List<Book> findMany(Function<DSLContext, List<Book>>contextConsumer) {
        try (var conn = DBUtils.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.H2, new Settings().withRenderNameStyle(RenderNameStyle.AS_IS));

            return contextConsumer.apply(create);
        }
        catch (DataAccessException|SQLException e) {
            throw new RepositoryException("Could not find all books", e);
        }
    }
*/
}
