package semestrovka.module.managers;

import semestrovka.module.exceptions.FileSystemManagerException;

import javax.servlet.http.HttpServletRequest;

public interface IFileSystemManager {
    String downloadFile(HttpServletRequest req) throws FileSystemManagerException;
    void copyFilesToWeb(String context);
}
