package de.kgalli.bookstore.book;

import de.kgalli.bookstore.config.DbConfig;
import de.kgalli.common.exceptions.RepositoryException;
import de.kgalli.common.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static de.kgalli.bookstore.tables.Books.BOOKS;



@Singleton
public class BookRepository {

    private DbConfig dbConfig;

    @Inject
    public BookRepository(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public Book create(Book book) {
        try (var conn = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.DEFAULT);

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
        try (var conn = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.DEFAULT);

            return create.select().from(BOOKS)
                    .where(BOOKS.ISBN.eq(isbn))
                    .fetchOptionalInto(Book.class);
        }
        catch (DataAccessException | SQLException e) {
            throw new RepositoryException("Could not find book by isbn", e);
        }
    }

    public List<Book> findAll() {
        try (var conn = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.DEFAULT); //, new Settings().withRenderNameStyle(RenderNameStyle.AS_IS));

            return create.select().from(BOOKS).fetchInto(Book.class);
        }
        catch (DataAccessException|SQLException e) {
            throw new RepositoryException("Could not find all book", e);
        }
    }

/*
    public List<Book> findAll() {
        Function<DSLContext, List<Book>> book = (ctx) ->
                ctx.select().from(BOOKS).fetchInto(Book.class);

        return findMany(book);
    }

    public List<Book> findMany(Function<DSLContext, List<Book>>contextConsumer) {
        try (var conn = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.H2, new Settings().withRenderNameStyle(RenderNameStyle.AS_IS));

            return contextConsumer.apply(create);
        }
        catch (DataAccessException|SQLException e) {
            throw new RepositoryException("Could not find all book", e);
        }
    }
*/
}
