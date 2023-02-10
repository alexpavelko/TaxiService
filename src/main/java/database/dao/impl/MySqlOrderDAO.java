package database.dao.impl;

import database.dao.OrderDAO;
import database.entity.Order;
import exception.DAOException;
import exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.dao.impl.FieldsConstants.*;
import static database.dao.impl.SqlRequestConstants.*;

public class MySqlOrderDAO implements OrderDAO {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(MySqlOrderDAO.class);

    public MySqlOrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Order get(long id) throws DAOException {
        Order order = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_ORDER_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                order = mapResultSet(rs);
        } catch (SQLException e) {
            logger.error("Error getting order by id.");
            throw new DAOException(e);
        }

        return order;
    }

    @Override
    public void add(Order order) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CREATE_ORDER)) {
            setStatementFields(order, pst);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add order." + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Order order) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ORDER)) {
            stmt.setString(setStatementFields(order, stmt), String.valueOf(order.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update order." + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_DELETE_ORDER)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete order." + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Order> getAll(String param) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ORDERS)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersNoFilter(int start, int recordsPerPage) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS)) {
            pst.setInt(1, start);
            pst.setInt(2, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all without filters." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersNoFilterOrderedDate(int start, int recordsPerPage) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_ORDERED_DATE)) {
            pst.setInt(1, start);
            pst.setInt(2, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all without filters ordered by date." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersNoFilterOrderedCost(int start, int recordsPerPage) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_ORDERED_COST)) {
            pst.setInt(1, start);
            pst.setInt(2, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all without filters ordered by cost." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersUserFilter(int start, int recordsPerPage, String userName) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER)) {
            pst.setString(1, userName);
            pst.setInt(2, start);
            pst.setInt(3, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by username." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersUserFilterOrderedDate(int start, int recordsPerPage, String userName) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_ORDERED_DATE)) {
            pst.setString(1, userName);
            pst.setInt(2, start);
            pst.setInt(3, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by user ordered by date." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersUserFilterOrderedCost(int start, int recordsPerPage, String userName) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_ORDERED_COST)) {
            pst.setString(1, userName);
            pst.setInt(2, start);
            pst.setInt(3, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by user ordered by cost." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersDateFilter(int start, int recordsPerPage, String date) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_DATE)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setInt(2, start);
            pst.setInt(3, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by date." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersDateFilterOrderedDate(int start, int recordsPerPage, String date) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_DATE_ORDERED_DATE)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setInt(2, start);
            pst.setInt(3, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by date ordered by date." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersDateFilterOrderedCost(int start, int recordsPerPage, String date) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_DATE_ORDERED_COST)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setInt(2, start);
            pst.setInt(3, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by date ordered by cost." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersUserAndDateFilter(int start, int recordsPerPage, String userName, String date) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setString(2, userName);
            pst.setInt(3, start);
            pst.setInt(4, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by user and date." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersUserAndDateFilterOrderedDate(int start, int recordsPerPage, String userName, String date) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE_ORDERED_DATE)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setString(2, userName);
            pst.setInt(3, start);
            pst.setInt(4, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by user and date ordered by date." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersUserAndDateFilterOrderedCost(int start, int recordsPerPage, String userName, String date) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE_ORDERED_COST)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setString(2, userName);
            pst.setInt(3, start);
            pst.setInt(4, recordsPerPage);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all orders filtered by user and date ordered by cost." + e.getMessage());
            throw new DAOException(e);
        }
        return orders;
    }

    public List<String> getAllLocations() throws DAOException {
        List<String> locations = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_LOCATIONS)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    locations.add(rs.getString("location_name"));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't get all locations." + e.getMessage());
            throw new DAOException(e);
        }
        return locations;
    }

    public Integer getLocIdByName(String location) throws DAOException {
        Integer locId = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_LOCATION_ID)) {
            pst.setString(1, location);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    locId = rs.getInt("location_id");
            }
        } catch (SQLException e) {
            logger.error("Can't get location id by name." + e.getMessage());
            throw new DAOException(e);
        }

        return locId;
    }

    public int getDistance(String loc_from, String loc_to) throws DAOException {
        int dist = 1;

        int loc1Id = getLocIdByName(loc_from);
        int loc2Id = getLocIdByName(loc_to);

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_DISTANCE)) {

            if (loc1Id <= loc2Id) {
                pst.setInt(1, loc1Id);
                pst.setInt(2, loc2Id);
            } else {
                pst.setInt(1, loc2Id);
                pst.setInt(2, loc1Id);
            }

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    dist = rs.getInt("distance");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return dist;
    }

    public int getNumberOfRows() throws DAOException {
        int num = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_ROWS_NUM)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    num = rs.getInt("COUNT(id)");
            }
        } catch (SQLException e) {
            logger.error("Can't get number of order rows." + e.getMessage());
            throw new DAOException(e);
        }

        return num;
    }

    public int getNumberOfRowsFilterUser(String userName) throws DAOException {
        int num = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_ROWS)) {
            pst.setString(1, userName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    num = rs.getInt("COUNT(id)");
            }
        } catch (SQLException e) {
            logger.error("Can't get number of order rows filtered by user." + e.getMessage());
            throw new DAOException(e);
        }

        return num;
    }

    public int getNumberOfRowsFilterDate(String date) throws DAOException {
        int num = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_DATE_ROWS)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    num = rs.getInt("COUNT(id)");
            }
        } catch (SQLException e) {
            logger.error("Can't get number of order rows filtered by date." + e.getMessage());
            throw new DAOException(e);
        }

        return num;
    }

    public int getNumberOfRowsFilterDateUser(String date, String userName) throws DAOException {
        int num = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_PAGINATION_ORDERS_WITH_USER_AND_DATE_ROWS)) {
            pst.setDate(1, Date.valueOf(LocalDate.parse(date)));
            pst.setString(2, userName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    num = rs.getInt("COUNT(id)");
            }
        } catch (SQLException e) {
            logger.error("Can't get number of order rows filtered by date and user." + e.getMessage());
            throw new DAOException(e);
        }

        return num;
    }

    private int setStatementFields(Order order, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setInt(++k, order.getUserId());
        stmt.setInt(++k, order.getCarId());
        stmt.setString(++k, order.getLocationFrom());
        stmt.setString(++k, order.getLocationTo());
        stmt.setDate(++k, Date.valueOf(order.getOrderDate()));
        stmt.setInt(++k, order.getPassengers());
        stmt.setBigDecimal(++k, order.getCost());
        return ++k;
    }

    private static Order mapResultSet(ResultSet rs) {
        Order order;
        try {
            order = new Order();
            order.setId(rs.getInt(ORDER_ID));
            order.setUserId(rs.getInt(ORDER_USER_ID));
            order.setCarId(rs.getInt(ORDER_CAR_ID));
            order.setLocationFrom(rs.getString(ORDER_LOCATION_FROM));
            order.setLocationTo(rs.getString(ORDER_LOCATION_TO));
            order.setOrderDate(rs.getDate(ORDER_ORDER_DATE).toLocalDate());
            order.setPassengers(rs.getInt(ORDER_PASSENGERS));
            order.setCost(rs.getBigDecimal(ORDER_COST));
            order.setUserName(rs.getString(ORDER_USER_NAME));
            order.setCarName(rs.getString(ORDER_CAR_NAME));
        } catch (SQLException e) {
            logger.error("Can't map order." + e.getMessage());
            throw new IllegalStateException(e);
        }
        return order;
    }
}
