package ${basePackage}.service;

import com.uhasoft.smurf.business.service.NameServiceImpl;
import ${basePackage}.dao.UserDao;
import ${basePackage}.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Service
public class UserServiceImpl extends NameServiceImpl<User, UserDao> implements UserService {

    public UserServiceImpl(UserDao dao) {
        super(dao);
    }
}
