package com.uhasoft.smurf.skeleton.controller;

import com.uhasoft.smurf.skeleton.generator.ModuleGenerator;
import com.uhasoft.smurf.skeleton.request.ProjectRo;
import com.uhasoft.smurf.skeleton.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("project")
public class ProjectController {

    /**
     * 理论上，该文件夹的文件内容可随意更改，只要符合SpEL语法即可，生成的项目结构基本上就是加上package之后的结构
     */
    private static final String TEMPLATE_FOLDER = "template";
    private static final String TARGET_PATH = "/Users/weihua/workspace/";

    @PostMapping
    public Response<Void> generate(@RequestBody ProjectRo project) {
        System.out.println(project.getBasePackage());
        System.out.println(project.getGroup());
        System.out.println(project.getVersion());
        System.out.println(project.getModule());
        return Response.success("Gr8! U Achieved the 1st Step!");
    }

    @GetMapping("download")
    public void download(@RequestParam Map<String, String> params, HttpServletResponse response) throws FileNotFoundException {
        File root = new File(getClass().getClassLoader().getResource(TEMPLATE_FOLDER).getFile());

        String target = TARGET_PATH + params.get("moduleName");
        new ModuleGenerator(root, new File(target), params).run();
        File zipFile = compress(target);
        try {
            // 取得文件名。
            String filename = zipFile.getName();
            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(zipFile));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + zipFile.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static File compress(String folder) {
        File sourceFolder = new File(folder);
        File zipFile = new File(folder + ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            writeZip(sourceFolder, "", zos);
            return zipFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.isDirectory()) {
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
    }

}
