package db;

import app.book.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class JDBCHelper {

    private static Logger logger = LoggerFactory.getLogger(JDBCHelper.class);

    public static Optional<Book> getBook(String isbn) {
        try (var con = getConnection();
            var prepStatement = con.prepareStatement("SELECT * FROM books WHERE isbn = ? LIMIT 1;")) {

            logger.info(prepStatement.toString());
            prepStatement.setString(1, isbn);
            Book book = null;

            try(var resObj = prepStatement.executeQuery()) {
                while(resObj.next()) {
                    book = new Book(
                            resObj.getString("title"),
                            resObj.getString("author"),
                            resObj.getString("isbn")
                    );
                }
            }

            return Optional.ofNullable(book);
        }
         catch (Exception sqlException) {
            sqlException.printStackTrace();
            return Optional.empty();
        }
    }

    public static final Connection getConnection() throws SQLException {
        //URI dbUri = new URI(System.getenv("DATABASE_URL"));
        var dbUrl = "jdbc:postgresql://localhost:9999/kgalli?user=kgalli&password=kgalli";
        var username = "kgalli";
        var password = "password";

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
