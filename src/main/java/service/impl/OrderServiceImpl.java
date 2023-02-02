package service.impl;

import database.dao.OrderDAO;
import database.entity.Car;
import database.entity.Order;
import dto.Converter;
import dto.OrderDTO;
import exception.DAOException;
import exception.ServiceException;
import exception.ValidateException;
import service.OrderService;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final static double DISCOUNT = 30;
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void addOrder(OrderDTO orderDTO)  throws ServiceException, ValidateException {
        try {
            orderDAO.add(Converter.convertDTOtoOder(orderDTO));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Counting the cost depending on  car cost and locations
     */
    public BigDecimal cost(BigDecimal costPerK, String loc_from, String loc_to) {
        BigDecimal cost = costPerK;

        BigDecimal dist = BigDecimal.valueOf(orderDAO.getDistance(loc_from, loc_to));

        return cost.multiply(dist);
    }

    /**
     * Counting the cost depending on  car cost and locations with discount
     */
    public BigDecimal costWithDiscount(BigDecimal idealCost, BigDecimal costPerK, String loc_from, String loc_to) {
        double cost = costPerK.doubleValue();
        double costIdeal = idealCost.doubleValue();

        double dist = orderDAO.getDistance(loc_from, loc_to);

        double diskVal = cost/100 * DISCOUNT;

        cost = cost*dist;

        return BigDecimal.valueOf(cost - diskVal);
    }

    /**
     * Counting the cost for double order depending on cars cost and locations
     */
    public BigDecimal costForTwoCars(List<Car> cars, String loc_from, String loc_to) {
        BigDecimal costPerK = cars.get(0).getCost();

        costPerK.add(cars.get(1).getCost());

        return cost(costPerK, loc_from, loc_to);
    }

    /**
     * Counting the cost for double order depending on cars cost and locations with discount
     */
    public BigDecimal costWithDiscountForTwoCars(BigDecimal idealCost, List<Car> cars, String loc_from, String loc_to) {
        BigDecimal costPerK = cars.get(0).getCost();

        costPerK.add(cars.get(1).getCost());

        return costWithDiscount(idealCost, costPerK, loc_from, loc_to);
    }

    @Override
    public List<String> getAllLocations() {
        return orderDAO.getAllLocations();
    }

    @Override
    public Integer getLocIdByName(String location) {
        return orderDAO.getLocIdByName(location);
    }

    @Override
    public int getDistance(String loc_from, String loc_to) {
        return orderDAO.getDistance(loc_from, loc_to);
    }

    @Override
    public int getNumberOfRows() {
        return orderDAO.getNumberOfRows();
    }

    @Override
    public int getNumberOfRowsFilterUser(String userName) {
        return orderDAO.getNumberOfRowsFilterUser(userName);
    }

    @Override
    public int getNumberOfRowsFilterDate(String date) {
        return orderDAO.getNumberOfRowsFilterDate(date);
    }

    @Override
    public int getNumberOfRowsFilterDateUser(String date, String userName) {
        return orderDAO.getNumberOfRowsFilterDateUser(date, userName);
    }

    @Override
    public List<Order> getOrdersNoFilter(int start, int recordsPerPage) {
        return orderDAO.getOrdersNoFilter(start, recordsPerPage);
    }

    @Override
    public List<Order> getOrdersNoFilterOrderedDate(int start, int recordsPerPage) {
        return orderDAO.getOrdersNoFilterOrderedDate(start, recordsPerPage);
    }

    @Override
    public List<Order> getOrdersNoFilterOrderedCost(int start, int recordsPerPage) {
        return orderDAO.getOrdersNoFilterOrderedCost(start, recordsPerPage);
    }

    @Override
    public List<Order> getOrdersUserFilter(int start, int recordsPerPage, String userName) {
        return orderDAO.getOrdersUserFilter(start, recordsPerPage, userName);
    }

    @Override
    public List<Order> getOrdersUserFilterOrderedDate(int start, int recordsPerPage, String userName) {
        return orderDAO.getOrdersUserFilterOrderedDate(start, recordsPerPage, userName);
    }

    @Override
    public List<Order> getOrdersUserFilterOrderedCost(int start, int recordsPerPage, String userName) {
        return orderDAO.getOrdersUserFilterOrderedCost(start, recordsPerPage, userName);
    }

    @Override
    public List<Order> getOrdersDateFilter(int start, int recordsPerPage, String date) {
        return orderDAO.getOrdersDateFilter(start, recordsPerPage, date);
    }

    @Override
    public List<Order> getOrdersDateFilterOrderedDate(int start, int recordsPerPage, String date) {
        return orderDAO.getOrdersDateFilterOrderedDate(start, recordsPerPage, date);
    }

    @Override
    public List<Order> getOrdersDateFilterOrderedCost(int start, int recordsPerPage, String date) {
        return orderDAO.getOrdersDateFilterOrderedCost(start, recordsPerPage, date);
    }

    @Override
    public List<Order> getOrdersUserAndDateFilter(int start, int recordsPerPage, String userName, String date) {
        return orderDAO.getOrdersUserAndDateFilter(start, recordsPerPage, userName, date);
    }

    @Override
    public List<Order> getOrdersUserAndDateFilterOrderedDate(int start, int recordsPerPage, String userName, String date) {
        return orderDAO.getOrdersUserAndDateFilterOrderedDate(start, recordsPerPage, userName, date);
    }

    @Override
    public List<Order> getOrdersUserAndDateFilterOrderedCost(int start, int recordsPerPage, String userName, String date) {
        return orderDAO.getOrdersUserAndDateFilterOrderedCost(start, recordsPerPage, userName, date);
    }

}
