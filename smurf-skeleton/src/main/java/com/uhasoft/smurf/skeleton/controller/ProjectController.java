package com.uhasoft.smurf.skeleton.controller;

import com.uhasoft.smurf.common.util.FileUtils;
import com.uhasoft.smurf.skeleton.generator.ModuleGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.uhasoft.smurf.skeleton.constant.Constant.TARGET_PATH;
import static com.uhasoft.smurf.skeleton.constant.Constant.TEMPLATE_FOLDER;


/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("project")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping("download")
    public void download(@RequestParam Map<String, String> params, HttpServletResponse response) {
        File root = new File(getClass().getClassLoader().getResource(TEMPLATE_FOLDER).getFile());

        String target = TARGET_PATH + params.get("moduleName");
        new ModuleGenerator(root, new File(target), params).run();
        File zipFile = FileUtils.compress(target, target + ".zip");
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipFile.getName().getBytes()));
        response.addHeader("Content-Length", "" + zipFile.length());
        response.setContentType("application/octet-stream");
        try {
            FileUtils.writeToOutputStream(zipFile, response.getOutputStream());
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
