package database.dao;

import exception.DAOException;
import database.entity.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarDao extends Dao<Car> {
    List<Car> findTwoCarsByType(String type, int passengers) throws DAOException;
    Car findAppropriateCar(String type, int passengers) throws DAOException;
    void updateStatus(long carId, int statusId) throws DAOException;
    Car findCarByPassengers(int passengers) throws DAOException;
    BigDecimal findAppropriateCarCost(String type, int passengers) throws DAOException;
}
