package ${basePackage}.entity;

import com.uhasoft.smurf.business.entity.NameEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Data
@Table(name = "t_tenant")
public class Tenant extends NameEntity {

  private String fullName;
  private String email;
  private String phone;
  private boolean dedicated;   //专享实例，包括数据库等资源

}
