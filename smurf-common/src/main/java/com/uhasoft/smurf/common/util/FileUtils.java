package com.uhasoft.smurf.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class FileUtils {

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

  public static File createFolder(File parent, String folder){
    File resFolder = new File(parent, folder);
    if(resFolder.mkdirs()){
      logger.info("{} folder created.", folder);
    }
    return resFolder;
  }
}
