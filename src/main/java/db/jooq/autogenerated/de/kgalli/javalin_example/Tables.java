/*
 * This file is generated by jOOQ.
 */
package de.kgalli.javalin_example;


import de.kgalli.javalin_example.tables.Books;
import de.kgalli.javalin_example.tables.FlywaySchemaHistory;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.books</code>.
     */
    public static final Books BOOKS = de.kgalli.javalin_example.tables.Books.BOOKS;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = de.kgalli.javalin_example.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;
}
