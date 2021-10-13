package semestrovka.module.managers;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

public abstract class AbstractFileSystemManager implements IFileSystemManager {
    protected static final String EXTENSION = ".jpeg";
    protected static final String PATH_TO_UPLOADS = ".." + File.separator + "uploads" + File.separator;
    protected static final String PATH_TO_WEB = ".." + File.separator + "webapps" + File.separator;
    protected static final String PATH_TO_IMG_IN_WEB = File.separator + "img" + File.separator + "products" + File.separator;
    protected final SecureRandom random;

    protected AbstractFileSystemManager() {
        random = new SecureRandom();
    }

    protected String generateRandomName() {
        return new BigInteger(130, random).toString(32);
    }
}
