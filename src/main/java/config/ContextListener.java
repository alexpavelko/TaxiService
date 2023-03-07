package config;

import database.connection.MyDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
    private static final String CONFIGURATION_PROPERTIES_FILE = "configuration.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(CONFIGURATION_PROPERTIES_FILE);
        logger.info("'Taxi Service' was started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MyDataSource.getConnectionsPool().close();
        java.sql.Driver mySqlDriver;
        try {
            System.out.println(new PropertiesConfig().getProperties().getProperty("connection.url"));
            mySqlDriver = DriverManager.getDriver(new PropertiesConfig().getProperties().getProperty("connection.url"));
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("DB was closed. 'Taxi Service' was closed");
    }
}