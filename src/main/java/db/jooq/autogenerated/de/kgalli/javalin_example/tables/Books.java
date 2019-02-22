/*
 * This file is generated by jOOQ.
 */
package de.kgalli.javalin_example.tables;


import de.kgalli.javalin_example.Public;
import de.kgalli.javalin_example.tables.records.BooksRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Books extends TableImpl<BooksRecord> {

    private static final long serialVersionUID = 1728599235;

    /**
     * The reference instance of <code>public.books</code>
     */
    public static final Books BOOKS = new Books();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BooksRecord> getRecordType() {
        return BooksRecord.class;
    }

    /**
     * The column <code>public.books.title</code>.
     */
    public final TableField<BooksRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>public.books.author</code>.
     */
    public final TableField<BooksRecord, String> AUTHOR = createField("author", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.books.isbn</code>.
     */
    public final TableField<BooksRecord, String> ISBN = createField("isbn", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * Create a <code>public.books</code> table reference
     */
    public Books() {
        this(DSL.name("books"), null);
    }

    /**
     * Create an aliased <code>public.books</code> table reference
     */
    public Books(String alias) {
        this(DSL.name(alias), BOOKS);
    }

    /**
     * Create an aliased <code>public.books</code> table reference
     */
    public Books(Name alias) {
        this(alias, BOOKS);
    }

    private Books(Name alias, Table<BooksRecord> aliased) {
        this(alias, aliased, null);
    }

    private Books(Name alias, Table<BooksRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Books(Table<O> child, ForeignKey<O, BooksRecord> key) {
        super(child, key, BOOKS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Books as(String alias) {
        return new Books(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Books as(Name alias) {
        return new Books(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Books rename(String name) {
        return new Books(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Books rename(Name name) {
        return new Books(name, null);
    }
}
