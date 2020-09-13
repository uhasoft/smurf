package com.uhasoft.smurf.demo.controller;

import com.uhasoft.smurf.common.model.Response;
import com.uhasoft.smurf.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${config.value.boolean:false}")
    private boolean configValueEnabled;

    @Value("${config.value.string}")
    private String configValueString;

    @RequestMapping("{id}")
    public Response<User> findById(@PathVariable String id){
        User user = new User();
        user.setId(id);
        user.setAge(23);
        user.setName("Name" + id);
        return Response.success(user);
    }

    @RequestMapping("config/value/boolean")
    public boolean validateBooleanConfigValue(){
        return configValueEnabled;
    }
    @RequestMapping("config/value/string")
    public String validateStringConfigValue(){
        return configValueString;
    }
}
