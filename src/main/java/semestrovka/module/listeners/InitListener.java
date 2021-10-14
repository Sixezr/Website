package semestrovka.module.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.*;
import semestrovka.module.repositories.*;
import semestrovka.module.services.IProfileService;
import semestrovka.module.services.ISecurityService;
import semestrovka.module.services.ProfileService;
import semestrovka.module.services.SecurityService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("WEB-INF/properties/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver"));
        hikariConfig.setUsername(properties.getProperty("db.user"));
        hikariConfig.setPassword(properties.getProperty("db.pass"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        UserRepository userRepository = new UserRepositoryJdbcImp(dataSource);
        ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource);
        CartRepository cartRepository = new CartRepositoryJdbcImpl(dataSource);
        AbstractFileSystemManager fileSystemManager = new FileSystemManager();
        ITokenManager tokenManager = new TokenManager();
        IAuthManager authManager = new AuthManager(userRepository, tokenManager, cartRepository);
        Validator validator = new Validator(userRepository, fileSystemManager, tokenManager);

        IProfileService profileService = new ProfileService(authManager, validator, userRepository);
        ISecurityService securityService = new SecurityService(authManager, validator, userRepository);

        servletContext.setAttribute(Constants.USER_REPOSITORY, userRepository);
        servletContext.setAttribute(Constants.PRODUCT_REPOSITORY, productRepository);
        servletContext.setAttribute(Constants.CART_REPOSITORY, cartRepository);
        servletContext.setAttribute(Constants.FILE_SYSTEM_MANAGER, fileSystemManager);
        servletContext.setAttribute(Constants.AUTH_MANAGER, authManager);
        servletContext.setAttribute(Constants.VALIDATOR, validator);

        servletContext.setAttribute(Constants.PROFILE_SERVICE, profileService);
        servletContext.setAttribute(Constants.SECUTRITY_SERVICE, securityService);
    }
}
