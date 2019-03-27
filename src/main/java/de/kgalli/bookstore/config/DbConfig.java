package de.kgalli.bookstore.config;

public class DbConfig {
    private String host = "localhost";
    private int port = 9999;
    private String user = "kgalli";
    private String password = "kgalli";
    private String database = "kgalli";
    private String dbUrl = String.format(
            "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
            host,
            port,
            database,
            user,
            password
    );

    public String getUrl() {
        return dbUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
}
