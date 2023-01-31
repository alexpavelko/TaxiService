package database.dao;

import exception.DAOException;
import exception.ValidateException;

import java.util.List;

public interface Dao<T> {
    T get(long id) throws DAOException;

    void add(T t) throws DAOException, ValidateException;

    void update(T t) throws DAOException, ValidateException;

    void delete(long id) throws DAOException, ValidateException;

    List<T> getAll(String param) throws DAOException;
}

