package database.dao;

import database.dao.impl.MySqlDAOFactory;

import javax.sql.DataSource;

public abstract class DAOFactory {
    private static DAOFactory instance;

    public static synchronized DAOFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new MySqlDAOFactory(dataSource);
        }
        return instance;
    }

    public abstract CarDAO getCarDao();

    public abstract OrderDAO getOrderDao();

    public abstract UserDAO getUserDao();

}
