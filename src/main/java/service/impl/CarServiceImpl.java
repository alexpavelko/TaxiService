package service.impl;

import database.dao.CarDAO;
import database.entity.Car;
import exception.DAOException;
import service.CarService;

import java.math.BigDecimal;
import java.util.List;

public class CarServiceImpl implements CarService {
    private final CarDAO carDao;

    public CarServiceImpl(CarDAO carDao) {
        this.carDao = carDao;
    }

    public static int getTypeId(String type) {

        switch (type.toLowerCase()) {
            case ("cheap"): {
                return 1;
            }
            case ("comfort"): {
                return 2;
            }
            case ("business"): {
                return 3;
            }
        }

        return 0;
    }

    @Override
    public void updateStatus(long carId, int statusId) throws DAOException {
        carDao.updateStatus(carId, statusId);
    }

    @Override
    public Car findCarByPassengers(int passengers) throws DAOException {
        return carDao.findCarByPassengers(passengers);
    }

    @Override
    public BigDecimal findAppropriateCarCost(String type, int passengers) throws DAOException {
        return carDao.findAppropriateCarCost(type, passengers);
    }

    @Override
    public List<Car> findTwoCarsByType(String type, int passengers) throws DAOException {
        return carDao.findTwoCarsByType(type, passengers);
    }

    @Override
    public Car findAppropriateCar(String type, int passengers) throws DAOException {
        return carDao.findAppropriateCar(type, passengers);
    }
}
