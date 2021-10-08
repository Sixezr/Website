package semestrovka.module.managers;

import org.apache.commons.io.FileUtils;
import semestrovka.module.exceptions.FileSystemManagerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;

public class FileSystemManager extends AbstractFileSystemManager {

    public FileSystemManager() {
       super();
    }

    public String downloadFile(HttpServletRequest req) throws FileSystemManagerException {
        File file = new File(PATH_TO_UPLOADS+generateRandomName()+EXTENSION);
        Part part;
        InputStream inputStream;
        try {
            file.getParentFile().mkdirs();
            part = req.getPart("photo");
            inputStream = part.getInputStream();
        } catch (IOException | ServletException e) {
            throw new FileSystemManagerException(e);
        }
        try {
            Files.copy(inputStream, file.toPath());
        } catch (IOException e) {
            throw new FileSystemManagerException(e);
        }
        copyFilesToWeb(req.getContextPath());
        return file.getName();
    }

    public void copyFilesToWeb(String context) {
        File directory = new File(PATH_TO_UPLOADS);
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
}