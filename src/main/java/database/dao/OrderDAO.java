package database.dao;

import database.entity.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order> {
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
