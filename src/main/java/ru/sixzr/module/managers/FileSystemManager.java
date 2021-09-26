package ru.sixzr.module.managers;

import org.apache.commons.io.FileUtils;
import ru.sixzr.exceptions.FileException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;

public class FileSystemManager {

    private static final String EXTENSION = ".jpeg";
    private static final String PATH_TO_UPLOADS = ".."+File.separator+"uploads"+File.separator;
    private static final String PATH_TO_WEB = ".."+File.separator+"webapps"+File.separator;
    private static final String PATH_TO_IMG_IN_WEB = File.separator+"img"+File.separator+"products"+ File.separator;

    private final SecureRandom random;

    public FileSystemManager() {
       random = new SecureRandom();
    }

    public String downloadFile(HttpServletRequest req) throws FileException {
        File file = new File(PATH_TO_UPLOADS+generateRandomName()+EXTENSION);
        Part part;
        InputStream inputStream;
        try {
            file.getParentFile().mkdirs();
            part = req.getPart("photo");
            inputStream = part.getInputStream();
        } catch (IOException | ServletException e) {
            throw new FileException(e);
        }
        try {
            Files.copy(inputStream, file.toPath());
        } catch (IOException e) {
            throw new FileException(e);
        }
        copyFilesToWeb(file.getParentFile(), req.getContextPath());
        return file.getName();
    }

    public void copyFilesToWeb(File directory, String context) {
        File webDirectory =  new File(PATH_TO_WEB+context+PATH_TO_IMG_IN_WEB);
        webDirectory.mkdirs();
        if (webDirectory.listFiles().length != directory.listFiles().length) {
            try {
                FileUtils.copyDirectory(directory, webDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateRandomName() {
        return new BigInteger(130, random).toString(32);
    }
}
