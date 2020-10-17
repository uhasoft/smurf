package com.uhasoft.smurf.demo.book.service;

import com.uhasoft.smurf.demo.book.model.Book;
import com.uhasoft.smurf.checker.annotation.Checked;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Component
public class BookService {

    private Map<String, Book> books = new HashMap<>();

    public boolean compare(Book book, @Checked Book another){
        return book.getName().equals(another.getName());
    }

    public boolean hasBook(@Checked Book book){
        return books.containsKey(book.getId());
    }

}
