package database.dao.impl;

import database.dao.*;

import javax.sql.DataSource;

public class MySqlDaoFactory extends DaoFactory {
    private final DataSource dataSource;
    private CarDao carDao;
    private OrderDao orderDao;
    private UserDao userDao;

    public MySqlDaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public CarDao getCarDao() {
        if (this.carDao == null) {
            this.carDao = new MySqlCarDao(dataSource);
        }
        return carDao;
    }

    @Override
    public OrderDao getOrderDao() {
        if (this.orderDao == null) {
            this.orderDao = new MySqlOrderDao(dataSource);
        }
        return orderDao;
    }

    @Override
    public UserDao getUserDao() {
        if (this.userDao == null) {
            this.userDao = new MySqlUserDao(dataSource);
        }
        return userDao;
    }
}
