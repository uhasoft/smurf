package com.uhasoft.smurf.demo.book.controller;

import com.uhasoft.smurf.checker.aop.CheckerException;
import com.uhasoft.smurf.common.model.Response;
import com.uhasoft.smurf.demo.book.model.Book;
import com.uhasoft.smurf.demo.book.service.BookService;
import com.uhasoft.smurf.checker.annotation.Checked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @Autowired
    private BookService bookService;

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
    public Response<String> save(@RequestBody @Checked("saveBook") Book book){
        for(String id : books.keySet()){
            if(bookService.compare(book, books.get(id))){
                return Response.failure(book.getName() + " has already existed.");
            }
        }
        if(bookService.hasBook(book)){
            return Response.failure(book.getName() + " has already existed.");
        }
        book.setId(UUID.randomUUID().toString());
        books.put(book.getId(), book);
        return Response.success("书籍保存成功");
    }

    @PutMapping
    public Response<String> update(@RequestBody @Checked("updateBook") Book book){
        books.put(book.getId(), book);
        return Response.success("书籍保存成功");
    }

    @ExceptionHandler
    public Response<String> handleCheckerException(CheckerException ex){
        return Response.failure(ex.getMessage());
    }
}
