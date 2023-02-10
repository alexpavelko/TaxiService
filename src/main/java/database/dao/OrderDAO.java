package database.dao;

import database.entity.Order;
import exception.DAOException;

import java.util.List;

public interface OrderDAO extends DAO<Order> {
    List<String> getAllLocations() throws DAOException;

    Integer getLocIdByName(String location) throws DAOException;

    int getDistance(String loc_from, String loc_to) throws DAOException;

    int getNumberOfRows() throws DAOException;

    ;

    int getNumberOfRowsFilterUser(String userName) throws DAOException;

    int getNumberOfRowsFilterDate(String date) throws DAOException;

    int getNumberOfRowsFilterDateUser(String date, String userName) throws DAOException;

    List<Order> getOrdersNoFilter(int start, int recordsPerPage) throws DAOException;

    List<Order> getOrdersNoFilterOrderedDate(int start, int recordsPerPage) throws DAOException;

    List<Order> getOrdersNoFilterOrderedCost(int start, int recordsPerPage) throws DAOException;

    List<Order> getOrdersUserFilter(int start, int recordsPerPage, String userName) throws DAOException;

    List<Order> getOrdersUserFilterOrderedDate(int start, int recordsPerPage, String userName) throws DAOException;

    List<Order> getOrdersUserFilterOrderedCost(int start, int recordsPerPage, String userName) throws DAOException;

    List<Order> getOrdersDateFilter(int start, int recordsPerPage, String date) throws DAOException;

    List<Order> getOrdersDateFilterOrderedDate(int start, int recordsPerPage, String date) throws DAOException;

    List<Order> getOrdersDateFilterOrderedCost(int start, int recordsPerPage, String date) throws DAOException;

    List<Order> getOrdersUserAndDateFilter(int start, int recordsPerPage, String userName, String date) throws DAOException;

    List<Order> getOrdersUserAndDateFilterOrderedDate(int start, int recordsPerPage, String userName, String date) throws DAOException;

    List<Order> getOrdersUserAndDateFilterOrderedCost(int start, int recordsPerPage, String userName, String date) throws DAOException;
}
