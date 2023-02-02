package database.dao.impl;

import database.dao.*;

import javax.sql.DataSource;

public class MySqlDAOFactory extends DAOFactory {
    private final DataSource dataSource;
    private CarDAO carDao;
    private OrderDAO orderDao;
    private UserDAO userDao;

    public MySqlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public CarDAO getCarDao() {
        if (this.carDao == null) {
            this.carDao = new MySqlCarDAO(dataSource);
        }
        return carDao;
    }

    @Override
    public OrderDAO getOrderDao() {
        if (this.orderDao == null) {
            this.orderDao = new MySqlOrderDAO(dataSource);
        }
        return orderDao;
    }

    @Override
    public UserDAO getUserDao() {
        if (this.userDao == null) {
            this.userDao = new MySqlUserDAO(dataSource);
        }
        return userDao;
    }
}
