package com.uhasoft.smurf.demo.controller;

import com.uhasoft.smurf.demo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("{id}")
    public User findById(@PathVariable String id){
        User user = new User();
        user.setId(id);
        user.setAge(23);
        user.setName("Name" + id);
        return user;
    }
}
