package service;

import database.entity.Car;
import database.entity.Order;
import dto.OrderDTO;
import exception.DAOException;
import exception.ServiceException;
import exception.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Oleksandr Pavelko
 */
public interface OrderService {
    void addOrder(OrderDTO orderDTO) throws ServiceException, ValidateException;
    String findCar(HttpServletRequest req, OrderDTO orderDTO) throws ServiceException, ValidateException;
    BigDecimal cost(BigDecimal costPerK, String loc_from, String loc_to);
    BigDecimal costWithDiscount(BigDecimal idealCost, BigDecimal costPerK, String loc_from, String loc_to);
    BigDecimal costForTwoCars(List<Car> cars, String loc_from, String loc_to);
    BigDecimal costWithDiscountForTwoCars(BigDecimal idealCost, List<Car> cars, String loc_from, String loc_to);

    List<String> getAllLocations();
    Integer getLocIdByName(String location);
    int getDistance(String loc_from, String loc_to);
    int getNumberOfRows();
    int getNumberOfRowsFilterUser(String userName);
    int getNumberOfRowsFilterDate(String date);
    int getNumberOfRowsFilterDateUser(String date, String userName);
    List<Order> getOrdersNoFilter(int start, int recordsPerPage);
    List<Order> getOrdersNoFilterOrderedDate(int start, int recordsPerPage);
    List<Order> getOrdersNoFilterOrderedCost(int start, int recordsPerPage);
    List<Order> getOrdersUserFilter(int start, int recordsPerPage, String userName);
    List<Order> getOrdersUserFilterOrderedDate(int start, int recordsPerPage, String userName);
    List<Order> getOrdersUserFilterOrderedCost(int start, int recordsPerPage, String userName);
    List<Order> getOrdersDateFilter(int start, int recordsPerPage, String date);
    List<Order> getOrdersDateFilterOrderedDate(int start, int recordsPerPage, String date);
    List<Order> getOrdersDateFilterOrderedCost(int start, int recordsPerPage, String date);
    List<Order> getOrdersUserAndDateFilter(int start, int recordsPerPage, String userName, String date);
    List<Order> getOrdersUserAndDateFilterOrderedDate(int start, int recordsPerPage, String userName, String date);
    List<Order> getOrdersUserAndDateFilterOrderedCost(int start, int recordsPerPage, String userName, String date);
}
