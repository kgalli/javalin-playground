package config;

public enum ApplicationProperties {
    DB_URL("de.kgalli.javalin-example.db.url"),
    DB_USERNAME("de.kgalli.javalin-example.db.username"),
    DB_PASSWORD("de.kgalli.javalin-example.db.password");

    private String displayName;

    ApplicationProperties(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

    @Override public String toString() { return displayName; }
}
