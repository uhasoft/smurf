package ${basePackage}.web.controller;

import com.uhasoft.smurf.business.web.NameController;
import ${basePackage}.entity.User;
import ${basePackage}.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("user")
public class UserController extends NameController<User, UserService> {

    public UserController(UserService service) {
        super(service);
    }

}
