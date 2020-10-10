package com.uhasoft.smurf.skeleton.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

import static com.uhasoft.smurf.skeleton.constant.Constant.TEMPLATE_FOLDER;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class FileGenerator {

  private static final Logger logger = LoggerFactory.getLogger(FileGenerator.class);

  private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

  static {
    configuration.setClassForTemplateLoading(FileGenerator.class, "/");
  }

  public static void generate(File source, File target, Map<String, String> params) {
    logger.info("generate file: {}", source.getName());
    try {
      String fileName = source.getAbsolutePath();
      Template template = configuration.getTemplate(fileName.substring(fileName.indexOf(TEMPLATE_FOLDER)));
      Writer out = new FileWriter(target);
      template.process(params, out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
