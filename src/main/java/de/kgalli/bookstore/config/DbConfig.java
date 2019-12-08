package de.kgalli.bookstore.config;

public class DbConfig {
    private String host = "localhost";
    private int port = 9999;
    private String user = "kgalli";
    private String password = "kgalli";
    private String database = "kgalli";
    private String dbUrl = String.format(
            "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
            getHost(),
            getPort(),
            getDatabase(),
            getUser(),
            getPassword()
    );

    public String getUrl() {
        return dbUrl;
    }

    public String getHost() {
        return getEnvOrDefaultValue("DB_HOST", host);
    }

    public int getPort() {
        return getEnvOrDefaultValue("DB_PORT", port);
    }

    public String getUser() {
        return getEnvOrDefaultValue("DB_USER", user);
    }

    public String getPassword() {
        return getEnvOrDefaultValue("DB_PASSWORD", password);
    }

    public String getDatabase() {
        return getEnvOrDefaultValue("DB_DATABASE", database);
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

    private String getEnvOrDefaultValue(String name, String defaultValue) {
        var envValue = System.getenv(name);

        return  envValue != null ? envValue : defaultValue;
    }

    private int getEnvOrDefaultValue(String name, int defaultValue) {
        var envValue = System.getenv(name);

        return  (envValue != null)
                ? Integer.valueOf(envValue)
                : defaultValue;
    }
}
