package ${basePackage};

import lombok.Data;

import java.util.Date;

/**
 *@author Weihua
 *@since 1.0.0
 */
@Data
public class BaseEntity {

  private String id;
  private String createdBy;
  private String lastModifiedBy;
  private Date createdAt;
  private Date lastModifiedAt;
  private boolean deleted;
  private int version;
}
