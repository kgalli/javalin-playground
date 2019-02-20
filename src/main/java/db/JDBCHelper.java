package db;

import app.book.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class JDBCHelper {

    private static Logger logger = LoggerFactory.getLogger(JDBCHelper.class);

    public static Optional<Book> getBook(String isbn) {
        Connection connObj = null;
        PreparedStatement prepStmtObj = null;
        ResultSet resultSetObj = null;
        try {
            connObj = getConnection();

            PreparedStatement prepStatement = connObj.prepareStatement("SELECT * FROM books WHERE isbn = ? LIMIT 1;");
            prepStatement.setString(1, isbn);

            logger.info(prepStatement.toString());

            ResultSet resObj = prepStatement.executeQuery();
            Book book = null;

            while(resObj.next()) {
                book = new Book(
                        resObj.getString("title"),
                        resObj.getString("author"),
                        resObj.getString("isbn")
                );
            }

            return Optional.ofNullable(book);
        }
         catch (Exception sqlException) {
            sqlException.printStackTrace();

            return Optional.empty();
        }
        finally {
            try {
                // Close Result Set Object
                if(resultSetObj!=null) {
                    resultSetObj.close();
                }
                // Close Prepared Statement Object
                if(prepStmtObj!=null) {
                    prepStmtObj.close();
                }
                // Close Connection Object
                if(connObj!=null) {
                    connObj.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        //URI dbUri = new URI(System.getenv("DATABASE_URL"));
        var dbUrl = "jdbc:postgresql://localhost:9999/kgalli?user=kgalli&password=kgalli";
        var username = "kgalli";
        var password = "password";

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
