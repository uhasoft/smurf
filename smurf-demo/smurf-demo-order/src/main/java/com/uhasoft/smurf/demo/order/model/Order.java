package com.uhasoft.smurf.demo.order.model;

import lombok.Data;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Data
public class Order {

    private String id;
    private List<Book> books;
}
