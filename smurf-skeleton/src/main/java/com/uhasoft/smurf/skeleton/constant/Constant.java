package com.uhasoft.smurf.skeleton.constant;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface Constant {

  String POM_FILE = "pom.xml";
  String MAIN_FOLDER = "src/main";
  String JAVA_FOLDER = "java";
  String RESOURCE_FOLDER = "resources";
  String TEST_FOLDER = "test";
  String BASE_PACKAGE = "basePackage";

  /**
   * 理论上，该文件夹的文件内容可随意更改，只要符合SpEL语法即可，生成的项目结构基本上就是加上package之后的结构
   */
  String TEMPLATE_FOLDER = "template";
  String TARGET_PATH = "$HOME/workspace/";

}
