package database.dao;

import database.entity.User;

public interface UserDao extends Dao<User>{
    User getByEmail(String email);

}
