package com.uhasoft.smurf.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static File createFolder(File parent, String folder) {
        File resFolder = new File(parent, folder);
        if (resFolder.mkdirs()) {
            logger.info("{} folder created.", folder);
        }
        return resFolder;
    }

    public static File compress(String source, String target) {
        File sourceFile = new File(source);
        File zipFile = new File(target);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            compressFolder(sourceFile, "", zos);
            return zipFile;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public static void compressFolder(File folder, String parentPath, ZipOutputStream zos) {
        parentPath += folder.getName() + File.separator;
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                compressFolder(file, parentPath, zos);
            } else {
                compressFile(file, parentPath, zos);
            }
        }
    }

    public static void compressFile(File file, String parentPath, ZipOutputStream zos) {
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
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public static void writeToOutputStream(File file, OutputStream outputStream) {
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            OutputStream os = new BufferedOutputStream(outputStream);
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
