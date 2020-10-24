package ${basePackage}.entity;

import com.uhasoft.smurf.business.entity.NameEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Data
@ToString
@Table(name = "t_user")
public class User extends NameEntity {

    private String password;
    private String fullName;

}
