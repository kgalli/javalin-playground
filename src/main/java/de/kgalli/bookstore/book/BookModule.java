package de.kgalli.bookstore.book;

import de.kgalli.common.Routing;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class BookModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BookController.class);
        bind(BookService.class);
        bind(BookRepository.class);
        Multibinder.newSetBinder(binder(), Routing.class).addBinding().to(BookRouting.class);
    }
}
