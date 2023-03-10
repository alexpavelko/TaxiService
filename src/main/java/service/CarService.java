package service;

import database.entity.Car;
import exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Oleksandr Pavelko
 */
public interface CarService {
    void updateStatus(long carId, int statusId) throws DAOException;
    List<Car> findTwoCarsByType(String type, int passengers) throws DAOException;
    Car findAppropriateCar(String type, int passengers) throws DAOException;
    Car findCarByPassengers(int passengers) throws DAOException;
    BigDecimal findAppropriateCarCost(String type, int passengers) throws DAOException;
}
