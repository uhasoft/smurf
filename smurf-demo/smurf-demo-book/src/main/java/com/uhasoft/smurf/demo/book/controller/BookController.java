package com.uhasoft.smurf.demo.book.controller;

import com.uhasoft.smurf.common.model.Response;
import com.uhasoft.smurf.demo.book.model.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("book")
public class BookController {

    @RequestMapping("{id}")
    public Response<Book> findById(@PathVariable String id){
        Book book = new Book();
        book.setAuthor("Weihua");
        book.setId(id);
        book.setName("Unnamed");
        book.setPress("Very Famous Press");
        return Response.success(book);
    }
}
