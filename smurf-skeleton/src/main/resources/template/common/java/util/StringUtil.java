package ${basePackage}.util;

/**
 *@author Weihua
 *@since 1.0.0
 */
public class StringUtil {

  private static boolean isEmpty(String str){
    return str == null || str.trim().length() == 0;
  }

  private static boolean isNotEmpty(String str){
    return str != null && str.trim().length() > 0;
  }
}
