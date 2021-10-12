package semestrovka.module.managers;

import semestrovka.module.exceptions.FileSystemManagerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public interface IFileSystemManager {
    String downloadFile(HttpServletRequest req) throws FileSystemManagerException;
    void downloadNewFile(HttpServletRequest req, String oldName) throws FileSystemManagerException;
    void copyFilesToWeb(String context);
}
