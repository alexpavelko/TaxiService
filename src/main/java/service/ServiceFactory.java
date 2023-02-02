package service;

import database.dao.DAOFactory;
import service.impl.*;

/**
 * @author Oleksandr Pavelko
 */
public class ServiceFactory {
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;

    public ServiceFactory(DAOFactory daoFactory) {
        this.userService = new UserServiceImpl(daoFactory.getUserDao());
        this.carService = new CarServiceImpl(daoFactory.getCarDao());
        this.orderService = new OrderServiceImpl(daoFactory.getOrderDao());
    }

    public static ServiceFactory getInstance(DAOFactory daoFactory) {
        return new ServiceFactory(daoFactory);
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
}
