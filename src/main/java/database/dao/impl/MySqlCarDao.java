package database.dao.impl;

import database.dao.CarDao;
import database.entity.Car;
import exception.DAOException;
import service.CarService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.dao.impl.FieldsConstants.*;
import static database.dao.impl.SqlRequestConstants.*;

public class MySqlCarDao implements CarDao {
    private final DataSource dataSource;

    public MySqlCarDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Car get(long id) throws DAOException {
        Car car = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CAR_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                car = mapResultSet(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return car;
    }

    @Override
    public void add(Car car) throws DAOException { // TODO: add method and test it
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            stmt = con.prepareStatement(SQL_INSERT_CAR, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            setStatementFieldsCar(car, stmt);
            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        car.setId(rs.getInt(1));
                    }
                }
            }
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void update(Car car) throws DAOException { // TODO: add method and test it
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(SQL_UPDATE_CAR);
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void delete(long id) throws DAOException { //TODO: test delete
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_DELETE_CAR)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Car> getAll(String param) throws DAOException {
        List<Car> cars = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_CARS)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return cars;
    }

    @Override
    public List<Car> findTwoCarsByType(String type, int passengers) throws DAOException {
        List<Car> cars = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_TWO_CARS_BY_TYPE)) {
            pst.setInt(1, passengers);
            pst.setInt(2, CarService.getTypeId(type));
            pst.setInt(3, CarService.getTypeId(type));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    cars.add(get(rs.getLong("x_id")));
                    cars.add(get(rs.getLong("y_id")));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return cars;
    }

    @Override
    public Car findAppropriateCar(String type, int passengers) throws DAOException {
        Car car = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_APPROPRIATE_CARS)) {
            pst.setInt(1, passengers);
            pst.setInt(2, 1);
            pst.setString(3, type);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    car = mapResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return car;
    }

    @Override
    public void updateStatus(long carId, int statusId) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_STATUS)) {
            pst.setInt(1, statusId);
            pst.setLong(2, carId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public Car findCarByPassengers(int passengers) throws DAOException {
        Car car = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_CAR_BY_PASSENGERS)) {
            pst.setInt(1, passengers);
            pst.setInt(2, 1);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    car = mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return car;
    }

    @Override
    public BigDecimal findAppropriateCarCost(String type, int passengers) throws DAOException {
        BigDecimal cost = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_APPROPRIATE_CAR_COST)) {
            pst.setInt(1, passengers);
            pst.setString(2, type);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    cost = rs.getBigDecimal(CAR_COST);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return cost;
    }

    private static Car mapResultSet(ResultSet rs) throws DAOException {
        Car car;
        try {
            car = new Car();
            car.setId(rs.getInt(CAR_ID));
            car.setName(rs.getString(CAR_NAME));
            car.setCost(rs.getBigDecimal(CAR_COST));
            car.setStatus(Car.Status.valueOf(rs.getString(CAR_STATUS).toUpperCase()));
            car.setCategory(Car.CarType.valueOf(rs.getString(CAR_TYPE).toUpperCase()));
            car.setPassengers(rs.getInt(CAR_PASSENGERS));

        } catch (SQLException e) {
            throw new DAOException(new IllegalStateException(e));
        }
        return car;
    }

    private int setStatementFieldsCar(Car car, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, car.getName());
        stmt.setString(++k, car.getCategory().name());
        stmt.setString(++k, car.getCost().toString());
        stmt.setString(++k, car.getStatus().name());
        stmt.setString(++k, String.valueOf(car.getPassengers()));
        return k;
    }

    private void close(AutoCloseable stmt) throws DAOException {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    private void rollback(Connection con) throws DAOException {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}

