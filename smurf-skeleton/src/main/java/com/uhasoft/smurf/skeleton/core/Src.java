package com.uhasoft.smurf.skeleton.core;

import com.uhasoft.smurf.skeleton.constant.Constant;
import com.uhasoft.smurf.skeleton.util.FileUtil;

import java.io.File;

/**
 @author Weihua
 @since 1.0.0 */
public class Src {

  private File javaFolder;
  private File resFolder;
  private File testFolder;

  public Src(File mainFolder){
    this.javaFolder = FileUtil.createFolder(mainFolder, Constant.JAVA_FOLDER);
    this.resFolder = FileUtil.createFolder(mainFolder, Constant.RESOURCE_FOLDER);
    this.testFolder = FileUtil.createFolder(mainFolder, Constant.TEST_FOLDER);
  }

  public File getJavaFolder() {
    return javaFolder;
  }

  public File getResFolder() {
    return resFolder;
  }

  public File getTestFolder() {
    return testFolder;
  }
}
