package com.uhasoft.smurf.demo.order.controller;

import com.uhasoft.smurf.common.annotation.Resource;
import com.uhasoft.smurf.common.model.Response;
import com.uhasoft.smurf.demo.order.feign.BookResource;
import com.uhasoft.smurf.demo.order.model.Book;
import com.uhasoft.smurf.demo.order.model.Order;
import com.uhasoft.smurf.ratelimit.core.exception.RateLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RequestMapping("order")
@RestController
@RefreshScope
public class OrderController {

    private static final Map<String, Order> ORDERS = new HashMap<>();

    @Autowired
    private BookResource bookResource;

    @PostMapping
    public Response<Order> save(Order order){
        order.setId(UUID.randomUUID().toString());
        ORDERS.put(order.getId(), order);
        return Response.success(order);
    }

    @GetMapping("book/{id}")
    public Response<Book> findBookById(@PathVariable String id){
        return bookResource.findById(id);
    }

    @GetMapping("/{id}")
    public Response<Order> findById(@PathVariable String id){
        System.out.println("Pass:" + new Date());
        return Response.success(ORDERS.get(id));
    }

    @GetMapping
    public Response<Collection<Order>> findAll(){
        return Response.success(ORDERS.values());
    }

    @ExceptionHandler
    public Response<String> handleException(RateLimitException ex){
        System.out.println("Limited:" + new Date());
        return Response.failure(ex.getMessage(), ex.getMessage());
    }

}
