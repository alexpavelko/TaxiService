package database.dao;

import database.entity.User;
import exception.DAOException;

public interface UserDAO extends DAO<User> {
    User getByEmail(String email) throws DAOException;

}
