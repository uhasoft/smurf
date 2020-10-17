package com.uhasoft.smurf.demo.book.controller;

import com.uhasoft.smurf.common.model.Response;
import com.uhasoft.smurf.demo.book.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("book")
public class BookController {

    private Map<String, Book> books = new HashMap<>();

    @Value("${server.port}")
    private int port;

    @GetMapping("{id}")
    public Response<Book> findById(@PathVariable String id){
        Book book = new Book();
        book.setAuthor("Weihua");
        book.setId(id);
        book.setName("Unnamed" + port);
        book.setPress("Very Famous Press");
        return Response.success(book);
    }

    @PostMapping
    public Response<String> save(@RequestBody Book book){
        book.setId(UUID.randomUUID().toString());
        books.put(book.getId(), book);
        return Response.success("书籍保存成功");
    }

    @PutMapping
    public Response<String> update(@RequestBody Book book){
        books.put(book.getId(), book);
        return Response.success("书籍保存成功");
    }
}
