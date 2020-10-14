package com.uhasoft.smurf.skeleton.generator;

import com.uhasoft.smurf.core.util.SpelUtil;
import com.uhasoft.smurf.skeleton.constant.Constant;
import com.uhasoft.smurf.skeleton.core.Src;
import com.uhasoft.smurf.common.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.uhasoft.smurf.skeleton.constant.Constant.BASE_PACKAGE;
import static com.uhasoft.smurf.skeleton.constant.Constant.JAVA_FOLDER;
import static com.uhasoft.smurf.skeleton.constant.Constant.RESOURCE_FOLDER;
import static com.uhasoft.smurf.skeleton.constant.Constant.TEMPLATE_FOLDER;
import static com.uhasoft.smurf.skeleton.constant.Constant.TEST_FOLDER;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Component
public class ModuleGenerator {

  private static final Logger logger = LoggerFactory.getLogger(ModuleGenerator.class);

  private File source;

  @Value("${smurf.skeleton.file.visible:}")
  private String fileVisible;

  private Map<String, String> fileVisibleMap = new LinkedHashMap<>();

  public ModuleGenerator(){
    this.source = new File(getClass().getClassLoader().getResource(TEMPLATE_FOLDER).getFile());
  }

  @PostConstruct
  public void init(){
    String[] visibilities = fileVisible.split(";");
    for(String visibility : visibilities){
      String[] kv = visibility.split(":");
      fileVisibleMap.put(kv[0], kv[1]);
    }
  }

  public void run(File target, Map<String, String> params) {
    if(!target.exists()){
      boolean created = target.mkdirs();
      if(created){
        logger.info("module folder created: {}", target.getName());
      } else {
        throw new RuntimeException("Failed to create module folder: " + target.getName());
      }
    }
    initModule(source, target, params);
  }

  public void initModule(File source, File target, Map<String, String> params){
    if(isPomModule(source)){
      File[] children = source.listFiles();
      assert children != null;
      for(File child : children){
        if(isModule(child)){
          Map<String, String> childParams = new HashMap<>(params);
          childParams.put(BASE_PACKAGE, params.get(BASE_PACKAGE).concat("." + child.getName()));
          String childModule = params.get("moduleName") + "-" + child.getName();
          initModule(child, new File(target, childModule), childParams);
        } else if(child.isDirectory()){
          copyFolder(child, new File(target, child.getName()), params);
        } else if(shouldGenerate(child, params)){
          FileGenerator.generate(child, new File(target, child.getName()), params);
        }
      }
    } else {
      Src targetSrc = generateSrc(target);
      File targetPackage = generatePackage(targetSrc.getJavaFolder(), params.get(BASE_PACKAGE));
      File sourcePackage = new File(source, JAVA_FOLDER);
      copyFolder(sourcePackage, targetPackage, params);
      copyFolder(new File(source, RESOURCE_FOLDER), targetSrc.getResFolder(), params);
      copyFolder(new File(source, TEST_FOLDER), targetSrc.getTestFolder(), params);
      File[] others = source.listFiles(file -> !file.getName().equals(JAVA_FOLDER) && !file.getName().equals(RESOURCE_FOLDER));
      if(others != null){
        Arrays.stream(others).forEach(file -> copy(file, new File(target, file.getName()), params));
      }
    }
  }

  public void copy(File source, File target, Map<String, String> params){
    if(source.isDirectory()){
      copyFolder(source, target, params);
    } else if(shouldGenerate(source, params)){
      FileGenerator.generate(source, target, params);
    }
  }

  public File generatePackage(File target, String pkg){
    StringTokenizer tokenizer = new StringTokenizer(pkg);
    File rootPackage = target;
    while(tokenizer.hasMoreTokens()){
      String folder = tokenizer.nextToken(".");
      rootPackage = new File(rootPackage, folder);
    }
    if(rootPackage.mkdirs()){
      logger.info("package {} created.", pkg);
    }
    return rootPackage;
  }

  public void copyFolder(File source, File target, Map<String, String> params){
    if(!target.exists()){
      if(target.mkdirs()){
        logger.info("folder: {} created.", target.getName());
      }
    }
    File[] children = source.listFiles();
    if(children != null){
      Arrays.stream(children).forEach(child -> copy(child, new File(target, child.getName()), params));
    }
  }

  /**
   判断是否为root模块，即含有子模块，pom的packaging属性为pom的模块
   @param source 源文件夹
   @return 是否为root模块
   */
  public static boolean isPomModule(File source){
    return isModule(source) && !new File(source, Constant.JAVA_FOLDER).exists();
  }

  /**
   判断是否为模块，即含有pom模版
   @param source 源文件夹
   @return 是否为模块
   */
  public static boolean isModule(File source){
    return source.isDirectory() && new File(source, Constant.POM_FILE).exists();
  }

  public boolean shouldGenerate(File file, Map<String, String> params){
    for(String fileSuffix : fileVisibleMap.keySet()){
      if(file.getAbsolutePath().endsWith(fileSuffix)){
        StandardEvaluationContext context = new StandardEvaluationContext();
        Map<String, Object> variables = new HashMap<>(params.size());
        params.forEach(variables::put);
        context.setVariables(variables);
        return SpelUtil.eval(fileVisibleMap.get(fileSuffix), context);
      }
    }
    return true;
  }

  public static Src generateSrc(File target){
    File main = FileUtils.createFolder(target, Constant.MAIN_FOLDER);
    return new Src(main);
  }


}
