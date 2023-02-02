package database.dao;

import database.entity.User;

public interface UserDAO extends DAO<User> {
    User getByEmail(String email);

}
