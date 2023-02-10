package service;

import database.entity.Car;
import database.entity.Order;
import dto.OrderDTO;
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
    BigDecimal cost(BigDecimal costPerK, String loc_from, String loc_to) throws ServiceException;
    BigDecimal costWithDiscount(BigDecimal idealCost, BigDecimal costPerK, String loc_from, String loc_to) throws ServiceException, ValidateException;
    BigDecimal costForTwoCars(List<Car> cars, String loc_from, String loc_to) throws ServiceException;
    BigDecimal costWithDiscountForTwoCars(BigDecimal idealCost, List<Car> cars, String loc_from, String loc_to) throws ServiceException , ValidateException;

    List<String> getAllLocations() throws ServiceException, ValidateException;
    Integer getLocIdByName(String location) throws ServiceException, ValidateException;
    int getDistance(String loc_from, String loc_to) throws ServiceException, ValidateException;
    int getNumberOfRows() throws ServiceException, ValidateException;
    int getNumberOfRowsFilterUser(String userName) throws ServiceException, ValidateException;
    int getNumberOfRowsFilterDate(String date) throws ServiceException, ValidateException;
    int getNumberOfRowsFilterDateUser(String date, String userName) throws ServiceException, ValidateException;
    List<Order> getOrdersNoFilter(int start, int recordsPerPage) throws ServiceException, ValidateException;
    List<Order> getOrdersNoFilterOrderedDate(int start, int recordsPerPage) throws ServiceException, ValidateException;
    List<Order> getOrdersNoFilterOrderedCost(int start, int recordsPerPage) throws ServiceException, ValidateException;
    List<Order> getOrdersUserFilter(int start, int recordsPerPage, String userName) throws ServiceException, ValidateException;
    List<Order> getOrdersUserFilterOrderedDate(int start, int recordsPerPage, String userName) throws ServiceException, ValidateException;
    List<Order> getOrdersUserFilterOrderedCost(int start, int recordsPerPage, String userName) throws ServiceException, ValidateException;
    List<Order> getOrdersDateFilter(int start, int recordsPerPage, String date) throws ServiceException, ValidateException;
    List<Order> getOrdersDateFilterOrderedDate(int start, int recordsPerPage, String date) throws ServiceException, ValidateException;
    List<Order> getOrdersDateFilterOrderedCost(int start, int recordsPerPage, String date) throws ServiceException, ValidateException;
    List<Order> getOrdersUserAndDateFilter(int start, int recordsPerPage, String userName, String date) throws ServiceException, ValidateException;
    List<Order> getOrdersUserAndDateFilterOrderedDate(int start, int recordsPerPage, String userName, String date) throws ServiceException, ValidateException;
    List<Order> getOrdersUserAndDateFilterOrderedCost(int start, int recordsPerPage, String userName, String date) throws ServiceException, ValidateException;
}
