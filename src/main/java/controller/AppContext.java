package controller;

import database.connection.MyDataSource;
import database.dao.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CarService;
import service.OrderService;
import service.ServiceFactory;
import service.UserService;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);

    private static AppContext appContext;
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;

    public static AppContext getAppContext() {
        return appContext;
    }

    public UserService getUserService() {
        return userService;
    }

    public CarService getCarService() {
        return carService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    private AppContext(String confPropertiesFile) {
        Properties properties = getProperties(confPropertiesFile);
        DataSource dataSource = MyDataSource.getDataSource(properties);
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        userService = serviceFactory.getUserService();
        carService = serviceFactory.getCarService();
        orderService = serviceFactory.getOrderService();
    }

    public static void createAppContext(String confPropertiesFile) {
        appContext = new AppContext(confPropertiesFile);
    }

    private static Properties getProperties(String confPropertiesFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = AppContext.class
                .getClassLoader()
                .getResourceAsStream(confPropertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}
