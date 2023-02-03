package dto;

import database.entity.Car;
import database.entity.Order;
import database.entity.User;

public class Converter {
    public static UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public static OrderDTO convertOrderToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCarName(order.getCarName());
        orderDTO.setUserName(order.getUserName());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setCarId(order.getCarId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setLocationTo(order.getLocationTo());
        orderDTO.setLocationFrom(order.getLocationFrom());
        orderDTO.setPassengers(order.getPassengers());
        orderDTO.setCost(order.getCost());

        return orderDTO;
    }

    public static CarDTO convertCarToDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setName(car.getName());
        carDTO.setCost(car.getCost());
        carDTO.setStatus(car.getStatus());
        carDTO.setCategory(car.getCategory());
        carDTO.setPassengers(car.getPassengers());

        return carDTO;
    }


    public static User convertDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }

    public static Order convertDTOtoOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCarName(orderDTO.getCarName());
        order.setUserName(orderDTO.getUserName());
        order.setUserId(orderDTO.getUserId());
        order.setCarId(orderDTO.getCarId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setLocationTo(orderDTO.getLocationTo());
        order.setLocationFrom(orderDTO.getLocationFrom());
        order.setPassengers(orderDTO.getPassengers());
        order.setCost(orderDTO.getCost());

        return order;
    }

    public static Car convertDTOToCar(CarDTO carDTO) {
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setName(carDTO.getName());
        car.setCost(carDTO.getCost());
        car.setStatus(carDTO.getStatus());
        car.setCategory(carDTO.getCategory());
        car.setPassengers(carDTO.getPassengers());

        return car;
    }
}
