package ru.sixzr.module.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.sixzr.module.helpers.Constants;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.managers.FileSystemManager;
import ru.sixzr.module.managers.SessionManager;
import ru.sixzr.module.managers.TokenManager;
import ru.sixzr.module.repositories.ProductRepository;
import ru.sixzr.module.repositories.ProductRepositoryJdbcImpl;
import ru.sixzr.module.repositories.UserRepository;
import ru.sixzr.module.repositories.UserRepositoryJdbcImp;

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
        FileSystemManager fileSystemManager = new FileSystemManager();
        SessionManager securityManager = new SessionManager(userRepository);
        TokenManager tokenManager = new TokenManager();
        Validator validator = new Validator(userRepository, fileSystemManager, tokenManager);

        servletContext.setAttribute(Constants.userRepository, userRepository);
        servletContext.setAttribute(Constants.productRepository, productRepository);
        servletContext.setAttribute(Constants.fileSystemManager, fileSystemManager);
        servletContext.setAttribute(Constants.sessionManager, securityManager);
        servletContext.setAttribute(Constants.validator, validator);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
