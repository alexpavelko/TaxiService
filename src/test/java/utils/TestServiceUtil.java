package utils;

import database.entity.Car;
import database.entity.Order;
import database.entity.User;
import dto.CarDTO;
import dto.OrderDTO;
import dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestServiceUtil {
    public User getAdmin() {
        return new User(
                "admin",
                "admin@gmail.com",
                User.Role.ADMIN,
                "Admin1235"
        );
    }

    public UserDTO getAdminDTO() {
        return new UserDTO(
                "admin",
                "admin@gmail.com",
                User.Role.ADMIN,
                "Admin1235");

    }

    public User getUser() {
        return new User(
                "user",
                "user@gmail.com",
                User.Role.USER,
                "User1235");

    }

    public UserDTO getUserDTO() {
        return new UserDTO(
                "user",
                "user@gmail.com",
                User.Role.USER,
                "User1235");

    }

    public CarDTO getCarDTO() {
        return new CarDTO(
                "Audi Q8",
                new BigDecimal("250"),
                Car.Status.BUSY,
                Car.CarType.COMFORT,
                2
        );
    }

    public Car getCar() {
        return new Car(
                "Audi Q8",
                new BigDecimal("250"),
                Car.Status.BUSY,
                Car.CarType.COMFORT,
                2
        );
    }

    public Order getOrder() {
        LocalDate localDate = LocalDate.of(2023, 2, 1);
        return new Order(
                "Audi Q8",
                0,
                0,
                localDate,
                "Дніпровський",
                "Хортицький",
                4,
                new BigDecimal(300)
        );
    }

    public OrderDTO getOrderDTO() {
        LocalDate localDate = LocalDate.of(2023, 2, 1);
        return new OrderDTO(
                0,
                "Audi Q8",
                0,
                0,
                localDate,
                "Дніпровський",
                "Хортицький",
                4,
                new BigDecimal(300),
                Car.CarType.COMFORT.toString()
        );
    }


    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(getCar());
        return cars;
    }

    public List<CarDTO> getCarDTOs() {
        List<CarDTO> carDTOs = new ArrayList<>();
        carDTOs.add(getCarDTO());
        return carDTOs;
    }
}
