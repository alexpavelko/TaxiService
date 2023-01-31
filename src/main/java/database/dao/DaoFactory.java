package database.dao;

import database.dao.impl.MySqlDaoFactory;

import javax.sql.DataSource;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new MySqlDaoFactory(dataSource);
        }
        return instance;
    }

    public abstract CarDao getCarDao();

    public abstract OrderDao getOrderDao();

    public abstract UserDao getUserDao();

}
